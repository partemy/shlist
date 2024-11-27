package dev.partemy.shlist.common.domain.repository

import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface IShoppingListRepository {
    suspend fun getAllShoppingLists(): Flow<ResultState<List<ShoppingList>>>
    suspend fun createShoppingList(name: String): Result<Nothing?>
    suspend fun deleteShoppingList(listId: Int): Result<Nothing?>
}