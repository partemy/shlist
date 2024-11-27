package dev.partemy.shlist.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.components.ShlistCreationDialog
import dev.partemy.shlist.ui.values.LargePadding
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    navigateToList: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect { event ->
                if (event is AuthViewEvent.SnackBarError) snackBarHostState.showSnackbar(message = event.message)
                if (event is AuthViewEvent.SuccessAuth) navigateToList()
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Content(
            onCreateKey = { viewModel.onTriggerEvent(AuthViewEvent.Auth(it)) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onCreateKey: (String?) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LargePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { isLogin = false; showDialog = true }
            ) {
                Text(text = ShlistResources.strings.createNewId)
            }
            Button(
                onClick = { isLogin = true; showDialog = true }
            ) {
                Text(text = ShlistResources.strings.loginById)
            }
        }
    }
    if (showDialog)
        ShlistCreationDialog(
            onDismissRequest = { showDialog = false },
            onCreateListClick = { val key = if (isLogin) it else null; onCreateKey(key) },
            labelText = if (isLogin) ShlistResources.strings.login else ShlistResources.strings.name,
            buttonText = if (isLogin) ShlistResources.strings.loginById else ShlistResources.strings.createNewId
        )
}