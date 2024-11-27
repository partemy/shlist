package dev.partemy.shlist.feature.main.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.usecase.CreateShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteKeyUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import dev.partemy.shlist.common.domain.usecase.GetKeyUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
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

    private val shoppingListsFlow = flow {
        emitAll(getAllShoppingListsUseCase.invoke())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ResultState.Loading()
    )

    init {
        getKey()
        getLists()
    }

    private fun getLists() {
        viewModelScope.launch {
            shoppingListsFlow.collect { result ->
                result.toState()
            }
        }
    }

    private fun getKey() = viewModelScope.launch {
        getKeyUseCase.invoke().collectLatest {
            setState { currentState.copy(key = it ?: "") }
        }
    }


    private fun createShoppingList(name: String) = viewModelScope.launch {
        val result = createShoppingListUseCase.invoke(name)
        if (result.isFailure) setSnackBarError(result)
    }

    private fun deleteShoppingList(listId: Int) = viewModelScope.launch {
        val result = deleteShoppingListUseCase.invoke(listId)
        if (result.isFailure) setSnackBarError(result)
    }

    private fun logOut() = viewModelScope.launch {
        deleteKeyUseCase.invoke()
    }

    private fun ResultState<List<ShoppingList>>.toState() {
        when (this) {
            is ResultState.Failure -> setState {
                currentState.copy(
                    lists = this@toState.data ?: emptyList(),
                    state = ScreenState.OFFLINE
                )
            }

            is ResultState.Loading -> setState {
                currentState.copy(
                    lists = this@toState.data ?: emptyList(),
                    state = ScreenState.LOADING
                )
            }

            is ResultState.Success -> setState {
                currentState.copy(
                    lists = this@toState.data,
                    state = ScreenState.SUCCESS)
            }
        }
    }

    private fun <T> setSnackBarError(result: Result<T>) = setEvent(
        MainViewEvent.SnackBackError(message = result.exceptionOrNull().toString())
    )
}

