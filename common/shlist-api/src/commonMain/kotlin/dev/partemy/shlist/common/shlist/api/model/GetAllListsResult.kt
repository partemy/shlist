package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class GetAllListsResult(
    val shop_list: List<ShoppingListDTO>,
    val success: Boolean
)