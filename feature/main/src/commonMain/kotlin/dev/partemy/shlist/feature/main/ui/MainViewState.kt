package dev.partemy.shlist.feature.main.ui

import androidx.compose.runtime.Stable
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.ui.base.IViewState

@Stable
data class MainViewState(
    val key: String = "",
    val lists: List<ShoppingList> = emptyList(),
    val state: ScreenState = ScreenState.LOADING,
) : IViewState

enum class ScreenState {
    LOADING,
    OFFLINE,
    SUCCESS;
}