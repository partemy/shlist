package dev.partemy.shlist.common.data.remote

import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem

interface IShoppingListRemoteDataSource {
    suspend fun getAllShoppingLists(key: String): Result<List<ShoppingList>>
    suspend fun getShoppingList(listId: Int): Result<List<ShoppingListItem>>
    suspend fun createShoppingList(key: String, name: String): Result<Int>
    suspend fun deleteShoppingList(listId: Int): Result<Boolean>
    suspend fun addToShoppingList(listId: Int, name: String, count: Int): Result<Int>
    suspend fun removeFromShoppingList(listId: Int, itemId: Int): Result<Nothing?>
    suspend fun crossOffItem(itemId: Int): Result<Int>
}