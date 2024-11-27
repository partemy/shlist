package dev.partemy.shlist.feature.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.components.OfflineIndicator
import dev.partemy.shlist.ui.components.ShlistCard
import dev.partemy.shlist.ui.components.ShlistCreationDialog
import dev.partemy.shlist.ui.components.ShlistFloatingButton
import dev.partemy.shlist.ui.components.ShlistLoadingIndicator
import dev.partemy.shlist.ui.values.LargePadding
import dev.partemy.shlist.ui.values.SmallPadding
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinInject(),
    navigateToList: (Pair<Int, String>) -> Unit,
    navigateToAuth: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            viewModel.uiEvent.collect { event ->
                if (event is MainViewEvent.SnackBackError) snackBarHostState.showSnackbar(message = event.message)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            ShlistFloatingButton(
                onActionClick = { showDialog = true },
                text = ShlistResources.strings.newList,
            )
        },
        topBar = {
            TopBar(
                key = uiState.value.key,
                onIconClick = { viewModel.onTriggerEvent(MainViewEvent.LogOut()); navigateToAuth() })
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding),
            state = uiState.value,
            onDeleteClick = { viewModel.onTriggerEvent(MainViewEvent.DeleteList(it)) },
            onCardClick = navigateToList
        )
    }
    if (uiState.value.state == ScreenState.LOADING)
        ShlistLoadingIndicator()

    if (showDialog) ShlistCreationDialog(
        onDismissRequest = { showDialog = false },
        onCreateListClick = {
            viewModel.onTriggerEvent(MainViewEvent.CreateList(it));
            showDialog = false
        },
        labelText = ShlistResources.strings.newList,
        buttonText = ShlistResources.strings.create,
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: MainViewState,
    onDeleteClick: (Int) -> Unit,
    onCardClick: (Pair<Int, String>) -> Unit,
) {
    if (state.lists.isEmpty())
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = ShlistResources.strings.noLists,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    else
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SmallPadding),
            modifier = modifier.fillMaxSize()
        ) {
            if (state.state == ScreenState.OFFLINE)
                item { OfflineIndicator() }

            items(state.lists, key = { item -> item.id }) { list ->
                ShlistCard(
                    name = list.name,
                    onIconClick = { onDeleteClick(list.id) },
                    onCardClick = { onCardClick(Pair(list.id, list.name)) },
                    isCrossed = false,
                )
            }
        }
}

@Composable
private fun TopBar(
    key: String,
    onIconClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(LargePadding)
    ) {
        Text(
            text = ShlistResources.strings.lists,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
        )
        Text(text = "key: $key")
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
                .clickable(onClick = { onIconClick() })
        )
    }
}