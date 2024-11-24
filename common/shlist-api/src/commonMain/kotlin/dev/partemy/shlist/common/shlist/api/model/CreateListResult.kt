package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateListResult(
    val list_id: Int,
    val success: Boolean,
)