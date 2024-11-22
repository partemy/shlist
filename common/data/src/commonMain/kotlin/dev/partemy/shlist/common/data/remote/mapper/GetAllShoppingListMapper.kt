package dev.partemy.shlist.common.data.remote.mapper

import dev.partemy.shlist.common.data.remote.model.GetAllListsResult
import dev.partemy.shlist.common.domain.model.ShoppingList

fun GetAllListsResult.toShoppingLists() = this.shop_list.map {
    ShoppingList(
        id = it.id,
        name = it.name,
        created = it.created
    )
}