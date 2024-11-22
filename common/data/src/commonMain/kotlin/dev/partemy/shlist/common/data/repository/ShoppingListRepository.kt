package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.util.MergeStrategy
import dev.partemy.shlist.common.data.util.RequestResponseMergeStrategy
import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.remote.api.ShoppingListService
import dev.partemy.shlist.common.data.remote.model.ShoppingListDTO
import dev.partemy.shlist.common.data.remote.model.ShoppingListItemDTO
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.domain.repository.IShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class ShoppingListRepository(
    private val shoppingListService: ShoppingListService,
    private val shoppingListLocalDataSource: IShoppingListLocalDataSource
) : IShoppingListRepository {


    fun ShoppingListDTO.toShoppingList() = ShoppingList(
        id = id,
        name = name,
        created = created
    )

    fun ShoppingListItemDTO.toShoppingListItem(listId: Int) = ShoppingListItem(
        id = id,
        listId = listId,
        name = name,
        isCrossed = is_crossed,
        created = created
    )

    override fun getAllShoppingLists(
        key: String,
    ): Flow<ResultState<List<ShoppingList>>> {
        val cachedShoppingLists = getAllListsFromDataBase()
        val remoteShoppingLists = getAllListsFromServer(key)
        val mergeStrategy: MergeStrategy<ResultState<List<ShoppingList>>> = RequestResponseMergeStrategy()

        return cachedShoppingLists.combine(remoteShoppingLists, mergeStrategy::merge)
    }

    private fun getAllListsFromServer(key: String): Flow<ResultState<List<ShoppingList>>> = flow {
        shoppingListService.getAllShoppingLists(key).collect { result ->
            when (result) {
                is ResultState.Failure -> emit(ResultState.Failure(exception = result.exception, data = emptyList()))
                is ResultState.Loading -> emit(ResultState.Loading(emptyList()))
                is ResultState.Success -> {
                    val resultData = result.data.shop_list.map { it.toShoppingList() }
                    shoppingListLocalDataSource.insertLists(resultData)
                    emit(ResultState.Success(resultData))
                }
            }
        }
    }

    private fun getAllListsFromDataBase(): Flow<ResultState<List<ShoppingList>>> =
        shoppingListLocalDataSource.getAllLists()
            .map<List<ShoppingList>, ResultState<List<ShoppingList>>> { ResultState.Success(it) }
            .onStart { ResultState.Loading(data = emptyList<ShoppingList>()) }
            .catch { ResultState.Failure(exception = Exception("get from db exception"), data = null) }

}
