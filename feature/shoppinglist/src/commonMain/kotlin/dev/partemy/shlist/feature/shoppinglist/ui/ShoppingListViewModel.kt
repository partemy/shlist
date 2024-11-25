package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.usecase.GetShoppingListItemsUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import dev.partemy.shlist.ui.base.IViewEvent
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val listId: Int,
    private val getShoppingListItemsUseCase: GetShoppingListItemsUseCase,
) : BaseViewModel<ShoppingListViewState, ShoppingListViewEvent>() {
    override fun createInitialState(): ShoppingListViewState = ShoppingListViewState()

    override fun onTriggerEvent(event: ShoppingListViewEvent) {}

    init {
        getShoppingListItems()
    }

    private fun getShoppingListItems() {
        viewModelScope.launch {
            getShoppingListItemsUseCase.invoke(listId).collect { result ->
                when (result) {
                    is ResultState.Failure -> setState { currentState.copy(list = result.data!!) }
                    is ResultState.Loading -> setState { currentState.copy(list = result.data!!) }
                    is ResultState.Success -> setState { currentState.copy(list = result.data!!) }
                }
            }
        }
    }
}

sealed class ShoppingListViewEvent : IViewEvent