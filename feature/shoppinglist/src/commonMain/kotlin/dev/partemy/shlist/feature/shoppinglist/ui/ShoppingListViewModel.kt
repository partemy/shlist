package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.usecase.AddShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.CrossOutShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.GetShoppingListItemsUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import dev.partemy.shlist.ui.base.IViewEvent
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val listId: Int,
    private val listName: String,
    private val getShoppingListItemsUseCase: GetShoppingListItemsUseCase,
    private val addShoppingListItemUseCase: AddShoppingListItemUseCase,
    private val deleteShoppingListItemUseCase: DeleteShoppingListItemUseCase,
    private val crossOutShoppingListItemUseCase: CrossOutShoppingListItemUseCase,
) : BaseViewModel<ShoppingListViewState, ShoppingListViewEvent>() {
    override fun createInitialState(): ShoppingListViewState = ShoppingListViewState()

    override fun onTriggerEvent(event: ShoppingListViewEvent) {
        when (event) {
            is ShoppingListViewEvent.AddItem -> addShoppingListItem(event.name, event.count)
            is ShoppingListViewEvent.CrossOutItem -> crossOutShoppingListItem(event.itemId)
            is ShoppingListViewEvent.DeleteItem -> deleteShoppingListItem(event.itemId)
        }
    }

    init {
        getShoppingListItems()
        setState { currentState.copy(title = listName) }
    }

    private fun getShoppingListItems() = viewModelScope.launch {
        getShoppingListItemsUseCase.invoke(listId).collect { result ->
            when (result) {
                is ResultState.Failure -> setState { currentState.copy(list = result.data!!) }
                is ResultState.Loading -> setState { currentState.copy(list = result.data!!) }
                is ResultState.Success -> setState { currentState.copy(list = result.data!!) }
            }
        }
    }


    private fun addShoppingListItem(name: String, count: Int) = viewModelScope.launch {
        val result = addShoppingListItemUseCase.invoke(listId, name, count)
    }


    private fun deleteShoppingListItem(itemId: Int) = viewModelScope.launch {
        val result = deleteShoppingListItemUseCase.invoke(itemId, listId)
    }


    private fun crossOutShoppingListItem(itemId: Int) = viewModelScope.launch {
        val result = crossOutShoppingListItemUseCase.invoke(itemId, listId)
    }
}

sealed class ShoppingListViewEvent : IViewEvent {
    class AddItem(val name: String, val count: Int) : ShoppingListViewEvent()
    class DeleteItem(val itemId: Int) : ShoppingListViewEvent()
    class CrossOutItem(val itemId: Int) : ShoppingListViewEvent()
}