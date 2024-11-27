package dev.partemy.shlist.navhost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.partemy.shlist.common.domain.usecase.GetKeyUseCase
import dev.partemy.shlist.feature.auth.navigation.AuthRoute
import dev.partemy.shlist.feature.auth.navigation.authScreen
import dev.partemy.shlist.feature.auth.navigation.navigateToAuth
import dev.partemy.shlist.feature.main.navigation.MainRoute
import dev.partemy.shlist.feature.main.navigation.mainScreen
import dev.partemy.shlist.feature.main.navigation.navigateToMain
import dev.partemy.shlist.feature.main.ui.MainViewModel
import dev.partemy.shlist.feature.shoppinglist.navigation.navigateToShoppingList
import dev.partemy.shlist.feature.shoppinglist.navigation.shoppingListScreen
import org.koin.compose.koinInject

@Composable
fun ShlistNavHost(
    navController: NavHostController,
) {
    val keyState by koinInject<GetKeyUseCase>().invoke().collectAsState(initial = "no_key")
    if (keyState == "no_key") {
        LoadingScreen()
    } else {
        val startDestination: Any = if (keyState.isNullOrBlank()) AuthRoute else MainRoute
        val mainViewModel: MainViewModel = koinInject()

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            mainScreen(
                navigateToList = { navController.navigateToShoppingList(args = it) },
                navigateToAuth = { navController.navigateToAuth() },
                viewModel = mainViewModel
            )
            shoppingListScreen { navController.popBackStack() }
            authScreen { navController.navigateToMain() }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(modifier = Modifier.size(128.dp))
    }
}