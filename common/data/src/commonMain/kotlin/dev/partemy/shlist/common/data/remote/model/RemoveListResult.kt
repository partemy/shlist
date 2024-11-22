package dev.partemy.shlist.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveListResult(
    val success: Boolean,
    val new_value: Boolean,
)