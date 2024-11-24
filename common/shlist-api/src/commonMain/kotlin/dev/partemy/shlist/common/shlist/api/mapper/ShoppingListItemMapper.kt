package dev.partemy.shlist.common.shlist.api.mapper

import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.shlist.api.model.ShoppingListItemDTO

fun ShoppingListItemDTO.toShoppingListItem(listId: Int) = ShoppingListItem(
    id, listId, name, is_crossed, created
)


fun Iterable<ShoppingListItemDTO>.toShoppingListItems(listId: Int) =
    this.map { it.toShoppingListItem(listId) }