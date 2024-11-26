package dev.partemy.shlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dev.partemy.shlist.common.domain.usecase.GetKeyUseCase
import dev.partemy.shlist.common.resources.ProvideShlistStrings
import dev.partemy.shlist.feature.auth.ui.AuthScreen
import dev.partemy.shlist.navhost.ShlistNavHost
import dev.partemy.shlist.ui.theme.ShlistTheme
import org.koin.compose.koinInject

@Composable
fun ShlistApp() {

    val navHostController = rememberNavController()
    val key = koinInject<GetKeyUseCase>().invoke().collectAsState(initial = "0")
    ProvideShlistStrings {
        ShlistTheme {
            if (key.value != "0") {
                if (key.value.isNullOrBlank()) AuthScreen(viewModel = koinInject())
                else ShlistNavHost(navHostController)
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(128.dp))
                }
            }
        }
    }
}


