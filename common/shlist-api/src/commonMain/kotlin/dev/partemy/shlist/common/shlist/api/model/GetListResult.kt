package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class GetListResult(
    val item_list: List<ShoppingListItemDTO>,
    val success: Boolean
)