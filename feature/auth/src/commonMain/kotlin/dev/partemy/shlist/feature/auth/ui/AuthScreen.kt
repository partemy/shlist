package dev.partemy.shlist.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.components.ShlistTextField
import dev.partemy.shlist.ui.values.LargePadding
import dev.partemy.shlist.ui.values.MediumPadding
import dev.partemy.shlist.ui.values.SmallPadding
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect { event ->
                if (event is AuthViewEvent.SnackBarError) snackBarHostState.showSnackbar(message = event.message)
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
        AuthDialog(isLogin, onDismissRequest = { showDialog = false }, onCreateKey)
}


@Composable
private fun AuthDialog(
    isLogin: Boolean,
    onDismissRequest: () -> Unit,
    onCreateKey: (String?) -> Unit,
) {
    var tfValue by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(SmallPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MediumPadding)
            ) {
                if (isLogin) {
                    ShlistTextField(
                        value = tfValue,
                        onValueChange = { tfValue = it },
                        placeholder = { Text(ShlistResources.strings.id) },
                    )
                    Button(
                        onClick = { onCreateKey(tfValue); onDismissRequest() }
                    ) {
                        Text(
                            ShlistResources.strings.login,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else
                    Button(
                        onClick = { onCreateKey(null) }
                    ) {
                        Text(
                            ShlistResources.strings.createNewId,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
            }
        }
    }
}