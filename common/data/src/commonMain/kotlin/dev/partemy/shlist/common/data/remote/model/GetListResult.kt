package dev.partemy.shlist.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetListResult(
    val item_list: List<ListItemDTO>,
    val success: Boolean
)