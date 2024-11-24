package dev.partemy.shlist.common.domain.repository

import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface IShoppingListItemsRepository {

    fun getAllShoppingListItems(listId: Int): Flow<ResultState<List<ShoppingListItem>>>
}