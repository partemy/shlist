package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CrossOffListItemResult(
    val rows_affected: Int,
    val success: Boolean
)