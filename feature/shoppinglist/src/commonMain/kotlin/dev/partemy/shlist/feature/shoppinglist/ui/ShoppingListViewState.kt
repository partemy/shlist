package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.compose.runtime.Stable
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.ui.base.IViewState

@Stable
data class ShoppingListViewState(
    val list: List<ShoppingListItem> = emptyList(),
    val title: String = "",
    val isLoading: Boolean = true,
    val isOffline: Boolean = false,
) : IViewState