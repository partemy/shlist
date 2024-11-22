package dev.partemy.shlist.common.data.remote.api

import dev.partemy.shlist.common.data.ResultState
import dev.partemy.shlist.common.data.remote.model.AddListItemResult
import dev.partemy.shlist.common.data.remote.model.CreateListResult
import dev.partemy.shlist.common.data.remote.model.CrossOffListItemResult
import dev.partemy.shlist.common.data.remote.model.GetAllListsResult
import dev.partemy.shlist.common.data.remote.model.GetListResult
import dev.partemy.shlist.common.data.remote.model.RemoveListItemResult
import dev.partemy.shlist.common.data.remote.model.RemoveListResult
import kotlinx.coroutines.flow.Flow

interface ShoppingListService {
    suspend fun createShoppingList(key:String, name: String): Flow<ResultState<CreateListResult>>
    suspend fun removeShoppingList(listId: Int): Flow<ResultState<RemoveListResult>>
    suspend fun addToShoppingList(listId: Int, name: String, count: Int): Flow<ResultState<AddListItemResult>>
    suspend fun removeFromShoppingList(listId: Int, itemId: Int): Flow<ResultState<RemoveListItemResult>>
    suspend fun crossOffItem(itemId: Int): Flow<ResultState<CrossOffListItemResult>>  // list_id?
    suspend fun getAllShoppingLists(key: String): Flow<ResultState<GetAllListsResult>>
    suspend fun getShoppingList(listId: Int): Flow<ResultState<GetListResult>>
}