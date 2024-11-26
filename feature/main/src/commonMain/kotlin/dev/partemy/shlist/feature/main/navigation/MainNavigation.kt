package dev.partemy.shlist.feature.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.partemy.shlist.feature.main.ui.MainScreen
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object MainRoute

fun NavController.navigateToMain(
    navOptions: NavOptions? = null
) = this.navigate(MainRoute, navOptions)

fun NavGraphBuilder.mainScreen(
    navigateToList: (Pair<Int, String>) -> Unit,
) {
    composable<MainRoute> {
        MainScreen(
            viewModel = koinInject(),
            navigateToList = navigateToList
        )
    }
}