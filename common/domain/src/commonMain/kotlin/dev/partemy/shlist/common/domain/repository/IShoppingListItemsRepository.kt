package dev.partemy.shlist.common.domain.repository

import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface IShoppingListItemsRepository {
    fun getAllShoppingListItems(listId: Int): Flow<ResultState<List<ShoppingListItem>>>
    suspend fun createShoppingListItem(listId: Int, name: String, count: Int) : Result<Int>
    suspend fun deleteShoppingListItem(itemId: Int, listId: Int) : Result<Nothing?>
    suspend fun crossOutShoppingListItem(itemId: Int, listId: Int) : Result<Nothing?>
}