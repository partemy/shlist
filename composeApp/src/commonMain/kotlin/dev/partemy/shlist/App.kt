package dev.partemy.shlist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.partemy.shlist.common.data.remote.api.impl.ShoppingListServiceImpl
import dev.partemy.shlist.common.data.remote.createHttpClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val httpClient: HttpClient = remember { createHttpClient() }
    val api = remember { ShoppingListServiceImpl(httpClient) }
    var text by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        text = api.getShoppingList(1).first().toString()
    }
    Column {
        Text(text)
    }
}