package dev.partemy.shlist.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.partemy.shlist.feature.main.navigation.MainRoute
import dev.partemy.shlist.feature.main.navigation.mainScreen
import dev.partemy.shlist.feature.shoppinglist.navigation.navigateToShoppingList
import dev.partemy.shlist.feature.shoppinglist.navigation.shoppingListScreen
import kotlinx.coroutines.MainScope

@Composable
fun ShlistNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        mainScreen { navController.navigateToShoppingList(listId = it) }
        shoppingListScreen { navController.popBackStack() }
    }
}