package dev.partemy.shlist.common.shlist.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveListResult(
    val success: Boolean,
    val new_value: Boolean,
)