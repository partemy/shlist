package dev.partemy.shlist.common.data.remote

import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem

interface IShoppingListRemoteDataSource {
    suspend fun getAllShoppingLists(key: String): Result<List<ShoppingList>>
    suspend fun getShoppingList(listId: Int): Result<List<ShoppingListItem>>
    suspend fun createShoppingList(key: String, name: String): Result<Int>
    suspend fun deleteShoppingList(listId: Int): Result<Boolean>
}