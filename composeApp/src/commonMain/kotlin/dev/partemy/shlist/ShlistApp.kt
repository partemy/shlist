package dev.partemy.shlist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dev.partemy.shlist.common.resources.ProvideShlistStrings
import dev.partemy.shlist.navhost.ShlistNavHost
import dev.partemy.shlist.ui.theme.ShlistTheme

@Composable
fun ShlistApp() {
    val navHostController = rememberNavController()
    ProvideShlistStrings {
        ShlistTheme {
            ShlistNavHost(navHostController)
        }
    }
}


