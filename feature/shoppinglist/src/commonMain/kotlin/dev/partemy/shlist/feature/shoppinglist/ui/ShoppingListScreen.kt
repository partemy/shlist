package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.koinInject

@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = koinInject()
) {
    val uiState = viewModel.uiState.collectAsState()
    LazyColumn {
        items(uiState.value.list) {
            Text(it.toString())
        }
    }
}