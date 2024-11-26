package dev.partemy.shlist.feature.main.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.usecase.CreateShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteKeyUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import dev.partemy.shlist.common.domain.usecase.GetKeyUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import dev.partemy.shlist.ui.base.IViewEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllShoppingListsUseCase: GetAllShoppingListsUseCase,
    private val createShoppingListUseCase: CreateShoppingListUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase,
    private val deleteKeyUseCase: DeleteKeyUseCase,
    private val getKeyUseCase: GetKeyUseCase,
) : BaseViewModel<MainViewState, MainViewEvent>() {
    override fun createInitialState(): MainViewState = MainViewState()
    override fun onTriggerEvent(event: MainViewEvent) {
        when (event) {
            is MainViewEvent.CreateList -> createShoppingList(event.name)
            is MainViewEvent.DeleteList -> deleteShoppingList(event.listId)
            is MainViewEvent.LogOut -> logOut()
            is MainViewEvent.SnackBackError -> {}
        }
    }

    init {
        getKey()
        getLists()
    }

    private var getListsJob: Job? = null

    private fun getLists() {
        getListsJob = viewModelScope.launch {
            getAllShoppingListsUseCase.invoke().collect { result ->
                when (result) {
                    is ResultState.Loading -> setState {
                        currentState.copy(lists = result.data!!, isLoading = true)
                    }

                    is ResultState.Failure -> setState {
                        currentState.copy(
                            lists = result.data!!,
                            isLoading = false,
                            isOffline = true
                        )
                    }

                    is ResultState.Success -> setState {
                        currentState.copy(lists = result.data, isLoading = false, isOffline = false)
                    }
                }
            }
        }
    }

    private fun getKey() = viewModelScope.launch {
        getKeyUseCase.invoke().collect {
            setState { currentState.copy(key = it ?: "") }
        }
    }


    private fun createShoppingList(name: String) = viewModelScope.launch {
        val result = createShoppingListUseCase.invoke(name)
        if (result.isFailure) setEvent(
            MainViewEvent.SnackBackError(
                message = result.exceptionOrNull().toString()
            )
        )
    }

    private fun deleteShoppingList(listId: Int) = viewModelScope.launch {
        val result = deleteShoppingListUseCase.invoke(listId)
        if (result.isFailure) setEvent(
            MainViewEvent.SnackBackError(
                message = result.exceptionOrNull().toString()
            )
        )
    }

    private fun logOut() = viewModelScope.launch {
        setState { currentState.copy(lists = emptyList()) }
        getListsJob?.cancel()
        deleteKeyUseCase.invoke()
    }
}

sealed class MainViewEvent : IViewEvent {
    class CreateList(val name: String) : MainViewEvent()
    class DeleteList(val listId: Int) : MainViewEvent()
    class LogOut() : MainViewEvent()
    class SnackBackError(val message: String) : MainViewEvent()
}