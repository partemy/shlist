package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository
import kotlinx.coroutines.flow.Flow

class ShoppingListItemsRepository(
    private val shoppingListRemoteDataSource: IShoppingListRemoteDataSource,
    private val shoppingListLocalDataSource: IShoppingListLocalDataSource
) : IShoppingListItemsRepository {
    override fun getAllShoppingListItems(listId: Int): Flow<ResultState<List<ShoppingListItem>>> {
        TODO("Not yet implemented")
    }

}