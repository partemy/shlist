package dev.partemy.shlist.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ListItemDTO(
    val created: String,
    val id: Int,
    val is_crossed: Boolean,
    val name: String
)