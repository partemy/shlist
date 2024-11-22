package dev.partemy.shlist.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetAllListsResult(
    val shop_list: List<ShoppingListDTO>,
    val success: Boolean
)