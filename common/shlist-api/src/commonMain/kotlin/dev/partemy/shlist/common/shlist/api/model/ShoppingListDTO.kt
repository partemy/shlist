package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListDTO(
    val created: String,
    val id: Int,
    val name: String
)