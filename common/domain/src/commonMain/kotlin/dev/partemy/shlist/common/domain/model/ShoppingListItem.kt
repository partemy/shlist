package dev.partemy.shlist.common.domain.model

data class ShoppingListItem(
    val id: Int,
    val listId: Int,
    val name: String,
    val isCrossed: Boolean,
    val created: String,
)
