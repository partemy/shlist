package dev.partemy.shlist

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.data.remote.api.ShoppingListService
import dev.partemy.shlist.common.data.remote.createHttpClient
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val bob: GetAllShoppingListsUseCase = koinInject()
    val text = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        text.value = bob.invoke(key = "vzv9aj").first().data.toString()
    }
    Text(text.value)
}

class VM(
    private val case: GetAllShoppingListsUseCase,
) : ViewModel() {
    var ui: MutableStateFlow<String> = MutableStateFlow(uiState().text)
    init {

    }


    data class uiState(
        val text: String = "123"
    )
}
