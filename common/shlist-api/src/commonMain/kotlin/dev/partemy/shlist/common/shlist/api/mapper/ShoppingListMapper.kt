package dev.partemy.shlist.common.shlist.api.mapper

import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.shlist.api.model.ShoppingListDTO

fun ShoppingListDTO.toShoppingList() = ShoppingList(
    id = id,
    name = name,
    created = created,
)

fun Iterable<ShoppingListDTO>.toShoppingLists() = this.map { it.toShoppingList() }

fun ShoppingList.toShoppingListDTO() = ShoppingListDTO(
    created, id, name
)