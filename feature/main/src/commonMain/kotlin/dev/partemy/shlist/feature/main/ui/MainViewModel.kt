package dev.partemy.shlist.feature.main.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.usecase.CreateShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import dev.partemy.shlist.ui.base.IViewEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllShoppingListsUseCase: GetAllShoppingListsUseCase,
    private val createShoppingListUseCase: CreateShoppingListUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase,
) : BaseViewModel<MainViewState, MainViewEvent>() {
    override fun createInitialState(): MainViewState = MainViewState()
    override fun onTriggerEvent(event: MainViewEvent) {
        when (event) {
            is MainViewEvent.OnCreateClick -> createShoppingList(event.name)
            is MainViewEvent.OnDeleteClick -> deleteShoppingList(event.listId)
        }
    }

    init {
        getLists()
    }

    private fun getLists() {
        viewModelScope.launch {
            getAllShoppingListsUseCase.invoke().collect { result ->
                when (result) {
                    is ResultState.Failure -> setState { currentState.copy(lists = result.data!!) }
                    is ResultState.Loading -> setState { currentState.copy(lists = result.data!!) }
                    is ResultState.Success -> setState { currentState.copy(lists = result.data) }
                }
            }
        }
    }

    private fun createShoppingList(name: String) =
        viewModelScope.launch {
            val result = createShoppingListUseCase.invoke(name)
            if (result.isFailure) setState { currentState.copy(text = "fail") }
            if (result.isSuccess) setState { currentState.copy(text = "succ") }
        }

    private fun deleteShoppingList(listId: Int) =
        viewModelScope.launch {
            deleteShoppingListUseCase.invoke(listId)
        }
}

sealed class MainViewEvent : IViewEvent {
    class OnCreateClick(val name: String) : MainViewEvent()
    class OnDeleteClick(val listId: Int) : MainViewEvent()
}