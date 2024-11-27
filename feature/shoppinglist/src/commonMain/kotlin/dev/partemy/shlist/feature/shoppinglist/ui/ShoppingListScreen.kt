package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel,
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect { event ->
                if (event is ShoppingListViewEvent.SnackBackError) snackBarHostState.showSnackbar(
                    message = event.message
                )
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            ShlistFloatingButton(
                onActionClick = { showDialog = true },
                text = ShlistResources.strings.newItem,
            )
        },
        topBar = {
            TopBar(title = uiState.value.title, navigateBack = navigateBack)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding),
            state = uiState.value,
            onCardClick = { viewModel.onTriggerEvent(ShoppingListViewEvent.CrossOutItem(it)) },
            onDeleteClick = { viewModel.onTriggerEvent(ShoppingListViewEvent.DeleteItem(it)) },
        )
    }
    if (showDialog)
        ShlistCreationDialog(
            onDismissRequest = { showDialog = false },
            onCreateListClick = { viewModel.onTriggerEvent(ShoppingListViewEvent.AddItem(it, 1)) },
            labelText = ShlistResources.strings.newItem,
            buttonText = ShlistResources.strings.create,
        )

    if (uiState.value.screenState == ScreenState.LOADING)
        ShlistLoadingIndicator()
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: ShoppingListViewState,
    onCardClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    if (state.list.isEmpty())
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = ShlistResources.strings.noItems,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    else
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SmallPadding),
            modifier = modifier.fillMaxSize()
        ) {
            if (state.screenState == ScreenState.OFFLINE)
                item { OfflineIndicator() }
            items(state.list, key = { item -> item.id }) { item ->
                ShlistCard(
                    name = item.name,
                    isCrossed = item.isCrossed,
                    onIconClick = { onDeleteClick(item.id) },
                    onCardClick = { onCardClick(item.id) },
                )
            }
        }
}

@Composable
private fun TopBar(
    title: String,
    navigateBack: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = SmallPadding)
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = navigateBack)

        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(LargePadding)
        )
    }
}
