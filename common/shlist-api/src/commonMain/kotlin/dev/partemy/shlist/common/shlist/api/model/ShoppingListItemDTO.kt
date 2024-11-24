package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItemDTO(
    val created: String,
    val id: Int,
    val is_crossed: Boolean,
    val name: String
)