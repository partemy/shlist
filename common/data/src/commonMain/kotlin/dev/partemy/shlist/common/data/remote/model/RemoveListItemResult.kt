package dev.partemy.shlist.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveListItemResult(
    val success: Boolean,
)
