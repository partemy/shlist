package dev.partemy.shlist.feature.shoppinglist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.components.ShlistTextField
import dev.partemy.shlist.ui.values.LargePadding
import dev.partemy.shlist.ui.values.MediumPadding
import dev.partemy.shlist.ui.values.SmallPadding
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import shlist.common.resources.generated.resources.delete
import shlist.common.resources.generated.resources.plus

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
                if (event is ShoppingListViewEvent.SnackBackError) snackBarHostState.showSnackbar(message = event.message)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            ShoppingListFloatingButton(onActionClick = {
                showDialog = true
            })
        },
        topBar = {
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
                    text = uiState.value.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(LargePadding)
                )
            }
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
        CreateListItemDialog(
            onDismissRequest = { showDialog = false },
            onCreateListClick = { viewModel.onTriggerEvent(ShoppingListViewEvent.AddItem(it, 1)) }
        )

    if (uiState.value.isLoading)
        Dialog(onDismissRequest = {}) {
            Box(modifier = Modifier.size(80.dp)) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        }

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
            if (state.isOffline)
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().height(40.dp)
                            .background(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = ShlistResources.strings.offlineMode,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(
                                SmallPadding
                            )
                        )
                    }
                }
            items(state.list, key = { item -> item.id }) { item ->
                ShoppingListItemCard(
                    name = item.name,
                    isCrossed = item.isCrossed,
                    onDeleteClick = { onDeleteClick(item.id) },
                    onCardClick = { onCardClick(item.id) },
                )
            }
        }
}

@Composable
private fun ShoppingListFloatingButton(
    modifier: Modifier = Modifier,
    onActionClick: (String) -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        onClick = { onActionClick.invoke("") },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = MediumPadding)
        ) {
            Icon(
                vectorResource(ShlistResources.drawable.plus),
                contentDescription = ShlistResources.strings.newItem,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(MediumPadding))
            Text(
                text = ShlistResources.strings.newItem,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CreateListItemDialog(
    onDismissRequest: () -> Unit,
    onCreateListClick: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var tfValue by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

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
                ShlistTextField(
                    value = tfValue,
                    onValueChange = { tfValue = it },
                    placeholder = { Text(ShlistResources.strings.newItem) },
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Button(
                    onClick = { onCreateListClick(tfValue); onDismissRequest() }
                ) {
                    Text(
                        ShlistResources.strings.create,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun ShoppingListItemCard(
    modifier: Modifier = Modifier,
    name: String,
    isCrossed: Boolean,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SmallPadding)
            .clickable(onClick = onCardClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(SmallPadding)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = if (isCrossed) TextDecoration.LineThrough else TextDecoration.None),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            Icon(
                vectorResource(ShlistResources.drawable.delete),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = { onDeleteClick.invoke() })
            )
        }
    }
}