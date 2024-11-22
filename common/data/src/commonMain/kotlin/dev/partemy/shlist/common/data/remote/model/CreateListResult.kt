package dev.partemy.shlist.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateListResult(
    val list_id: Int,
    val success: Boolean,
)