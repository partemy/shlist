package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.domain.usecase.AddShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.CrossOutShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.GetShoppingListItemsUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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
            is ShoppingListViewEvent.SnackBackError -> {}
        }
    }

    private val shoppingListsItemsFlow = getShoppingListItemsUseCase.invoke(listId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ResultState.Loading())

    init {
        getShoppingListItems()
        setState { currentState.copy(title = listName) }
    }

    private fun getShoppingListItems() = viewModelScope.launch {
        shoppingListsItemsFlow.collect { result ->
            result.toState()
        }
    }


    private fun addShoppingListItem(name: String, count: Int) = viewModelScope.launch {
        val result = addShoppingListItemUseCase.invoke(listId, name, count)
        if (result.isFailure) setSnackBarError(result)
    }


    private fun deleteShoppingListItem(itemId: Int) = viewModelScope.launch {
        val result = deleteShoppingListItemUseCase.invoke(itemId, listId)
        if (result.isFailure) setSnackBarError(result)
    }


    private fun crossOutShoppingListItem(itemId: Int) = viewModelScope.launch {
        val result = crossOutShoppingListItemUseCase.invoke(itemId, listId)
        if (result.isFailure) setSnackBarError(result)
    }

    private fun ResultState<List<ShoppingListItem>>.toState() {
        when (this) {
            is ResultState.Failure -> setState {
                currentState.copy(
                    list = this@toState.data ?: emptyList(),
                    screenState = ScreenState.OFFLINE
                )
            }

            is ResultState.Loading -> setState {
                currentState.copy(
                    list = this@toState.data ?: emptyList(),
                    screenState = ScreenState.LOADING
                )
            }

            is ResultState.Success -> setState {
                currentState.copy(list = this@toState.data, screenState = ScreenState.SUCCESS)
            }
        }
    }

    private fun <T> setSnackBarError(result: Result<T>) = setEvent(
        ShoppingListViewEvent.SnackBackError(message = result.exceptionOrNull().toString())
    )

}



