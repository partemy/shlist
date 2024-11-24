package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.data.util.MergeStrategy
import dev.partemy.shlist.common.data.util.RequestResponseMergeStrategy
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository
import dev.partemy.shlist.common.domain.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class ShoppingListItemsRepository(
    private val shoppingListRemoteDataSource: IShoppingListRemoteDataSource,
    private val shoppingListLocalDataSource: IShoppingListLocalDataSource
) : IShoppingListItemsRepository {

    override fun getAllShoppingListItems(listId: Int): Flow<ResultState<List<ShoppingListItem>>> {
        val cachedShoppingListItems = getAllListItemsFromServer(listId)
        val remoteShoppingListItems = getAllListItemsFromDatabase(listId)
        val mergeStrategy: MergeStrategy<ResultState<List<ShoppingListItem>>> =
            RequestResponseMergeStrategy()

        return cachedShoppingListItems.combine(remoteShoppingListItems, mergeStrategy::merge)
    }

    private fun getAllListItemsFromServer(listId: Int): Flow<ResultState<List<ShoppingListItem>>> {
        val apiRequest =
            flow { emit(shoppingListRemoteDataSource.getShoppingList(listId)) }
                .onEach { result ->
                    if (result.isSuccess) shoppingListLocalDataSource.insertListItems(result.getOrThrow())
                }
                .map { it.toRequestResult() }
        val start = flowOf<ResultState<List<ShoppingListItem>>>(ResultState.Loading())
        return merge(apiRequest, start)
    }

    private fun getAllListItemsFromDatabase(listId: Int): Flow<ResultState<List<ShoppingListItem>>> =
        shoppingListLocalDataSource.getAllListItems(listId)
            .map<List<ShoppingListItem>, ResultState<List<ShoppingListItem>>> {
                ResultState.Success(it)
            }
            .onStart { ResultState.Loading(data = null) }
            .catch {
                ResultState.Failure(
                    exception = Exception("error: get list items from db"),
                    data = null
                )
            }

}