package dev.partemy.shlist.common.data.local

import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface IShoppingListLocalDataSource {
    fun getAllLists(): Flow<List<ShoppingList>>
    fun getAllListItems(listId: Int): Flow<List<ShoppingListItem>>
    suspend fun insertList(list: ShoppingList)
    suspend fun replaceAllLists(lists: List<ShoppingList>)
    suspend fun replaceListItems(items: List<ShoppingListItem>, listId: Int)
    suspend fun deleteList(listId: Int)
}