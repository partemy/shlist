package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveListItemResult(
    val success: Boolean,
)
