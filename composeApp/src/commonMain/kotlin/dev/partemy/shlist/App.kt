package dev.partemy.shlist

import androidx.compose.runtime.Composable
import dev.partemy.shlist.common.resources.ProvideShlistStrings
import dev.partemy.shlist.feature.shoppinglist.ui.ShoppingListScreen
import dev.partemy.shlist.ui.theme.ShlistTheme

@Composable
fun App() {
    ProvideShlistStrings {
        ShlistTheme {
            ShoppingListScreen()
        }
    }
}


