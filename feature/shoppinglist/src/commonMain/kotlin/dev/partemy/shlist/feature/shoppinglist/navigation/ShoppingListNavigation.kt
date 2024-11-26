package dev.partemy.shlist.feature.shoppinglist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.partemy.shlist.feature.shoppinglist.ui.ShoppingListScreen
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Serializable
data class ShoppingListRoute(
    val listId: Int,
    val name: String,
)

fun NavController.navigateToShoppingList(
    navOptions: NavOptions? = null,
    args: Pair<Int, String>
) = this.navigate(ShoppingListRoute(listId = args.first, name = args.second), navOptions)

fun NavGraphBuilder.shoppingListScreen(
    navigateBack: () -> Unit
) {
    composable<ShoppingListRoute> { backStackEntry ->
        val list: ShoppingListRoute = backStackEntry.toRoute()
        ShoppingListScreen(
            viewModel = koinInject { parametersOf(list.listId, list.name) },
            navigateBack = navigateBack
        )
    }
}