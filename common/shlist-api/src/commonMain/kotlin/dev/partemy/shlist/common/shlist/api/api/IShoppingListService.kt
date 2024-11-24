package dev.partemy.shlist.common.shlist.api.api

import dev.partemy.shlist.common.shlist.api.model.AddListItemResult
import dev.partemy.shlist.common.shlist.api.model.CreateListResult
import dev.partemy.shlist.common.shlist.api.model.CrossOffListItemResult
import dev.partemy.shlist.common.shlist.api.model.GetAllListsResult
import dev.partemy.shlist.common.shlist.api.model.GetListResult
import dev.partemy.shlist.common.shlist.api.model.RemoveListItemResult
import dev.partemy.shlist.common.shlist.api.model.RemoveListResult

interface IShoppingListService {

    suspend fun createShoppingList(key: String, name: String): Result<CreateListResult>
    suspend fun removeShoppingList(listId: Int): Result<RemoveListResult>
    suspend fun addToShoppingList(listId: Int, name: String, count: Int): Result<AddListItemResult>
    suspend fun removeFromShoppingList(listId: Int, itemId: Int): Result<RemoveListItemResult>
    suspend fun crossOffItem(itemId: Int): Result<CrossOffListItemResult>
    suspend fun getAllShoppingLists(key: String): Result<GetAllListsResult>
    suspend fun getShoppingList(listId: Int): Result<GetListResult>

}