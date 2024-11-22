package dev.partemy.shlist.common.database.mapper

import dev.partemy.shlist.common.database.entity.ShoppingListItemDBO
import dev.partemy.shlist.common.domain.model.ShoppingListItem

fun ShoppingListItemDBO.toShoppingListItem() =
    ShoppingListItem(id, listId, name, isCrossed, created)

fun Iterable<ShoppingListItemDBO>.toShoppingListItems() = this.map { it.toShoppingListItem() }

fun ShoppingListItem.toShoppingListItemDBO() =
    ShoppingListItemDBO(id, listId, name, isCrossed, created)