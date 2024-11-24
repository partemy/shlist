package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class AddListItemResult(
    val item_id: Int,
    val success: Boolean
)