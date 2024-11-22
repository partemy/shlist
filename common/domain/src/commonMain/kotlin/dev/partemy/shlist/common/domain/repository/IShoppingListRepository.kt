package dev.partemy.shlist.common.domain.repository

import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface IShoppingListRepository {

    fun getAllShoppingLists(key: String): Flow<ResultState<List<ShoppingList>>>
}