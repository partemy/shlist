package dev.partemy.shlist.common.data.local

import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface IShoppingListLocalDataSource {

    fun getAllLists(): Flow<List<ShoppingList>>

    fun getAllListItems(listId: Int): Flow<List<ShoppingListItem>>

    suspend fun insertList(list: ShoppingList)

    suspend fun insertLists(lists: List<ShoppingList>)

    suspend fun insertListItem(item: ShoppingListItem)

    suspend fun insertListItems(item: List<ShoppingListItem>)

    suspend fun replaceAllLists(item: List<ShoppingList>)

    suspend fun deleteList(listId: Int)

    suspend fun deleteListItem(item: ShoppingListItem)

    suspend fun crossOutListItem(id: Int, isCrossed: Boolean)

}