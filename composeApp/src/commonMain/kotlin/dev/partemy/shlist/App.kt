package dev.partemy.shlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.repository.IShoppingListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    ShoppingListScreen(viewModel = ShoppingListViewModel(repository = koinInject()))
}

@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel) {
    var items by remember { mutableStateOf(emptyList<ShoppingList>()) }
    val ui = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadShoppingLists()
        viewModel.uiState.collect {
            items = it.list
        }
    }

    LazyColumn {
        items(items) { item ->
            Text(
                text = item.toString(),
                modifier = Modifier.clickable {
                    viewModel.deleteShoppingList(
                        listId = item.id,
                    )
                }
            )
        }
        item {
            Button(onClick = { viewModel.createShoppingList(name = "bob") }) {
                Text("add new")
            }
        }
        item {
            Button(onClick = { viewModel.setKEy(); viewModel.loadShoppingLists() }) {
                Text(text = ui.value.text)
            }
        }
    }


}


class ShoppingListViewModel(
    private val repository: IShoppingListRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(StateFF())
    val uiState = _uiState.asStateFlow()

    fun setKEy() {
        viewModelScope.launch {
            repository.setKey("vzv9aj")
        }
    }

    fun loadShoppingLists() {
        viewModelScope.launch {
            repository.getAllShoppingLists().collect {
                when (it) {
                    is ResultState.Failure -> _uiState.value = _uiState.value.copy(list = it.data!!)
                    is ResultState.Loading -> _uiState.value = _uiState.value.copy(list = it.data!!)
                    is ResultState.Success -> _uiState.value = _uiState.value.copy(list = it.data!!)
                }
            }
        }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            val res =repository.createShoppingList(name)
            if (res.isFailure) _uiState.value = _uiState.value.copy(text = res.exceptionOrNull().toString())
        }
    }

    fun deleteShoppingList(listId: Int) {
        viewModelScope.launch {
            repository.deleteShoppingList(listId)
        }
    }
}

@Stable
data class StateFF(
    val list: List<ShoppingList> = emptyList(),
    val text: String = ""
)

