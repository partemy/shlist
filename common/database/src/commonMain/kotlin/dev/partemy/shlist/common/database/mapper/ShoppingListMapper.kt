package dev.partemy.shlist.common.database.mapper

import dev.partemy.shlist.common.database.entity.ShoppingListDBO
import dev.partemy.shlist.common.domain.model.ShoppingList

fun ShoppingListDBO.toShoppingList() = ShoppingList(id, name, created)

fun ShoppingList.toShoppingListDBO() = ShoppingListDBO(id, name, created)

fun Iterable<ShoppingList>.toShoppingListsBDO() = this.map { it.toShoppingListDBO() }