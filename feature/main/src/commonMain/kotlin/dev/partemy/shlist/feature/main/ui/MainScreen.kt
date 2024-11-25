package dev.partemy.shlist.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.components.ShlistTextField
import dev.partemy.shlist.ui.values.MediumPadding
import dev.partemy.shlist.ui.values.SmallPadding
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import shlist.common.resources.generated.resources.delete
import shlist.common.resources.generated.resources.plus

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinInject(),
    navigateToList: (Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = { ShoppingListFloatingButton(onActionClick = { showDialog = true }) }
    ) {
        Content(
            state = uiState.value,
            onDeleteClick = { viewModel.onTriggerEvent(MainViewEvent.OnDeleteClick(it)) },
            onCardClick = navigateToList
        )
    }

    if (showDialog) CreateListDialog(
        onDismissRequest = { showDialog = false },
        onCreateListClick = {
            viewModel.onTriggerEvent(MainViewEvent.OnCreateClick(it));
            showDialog = false
        }
    )
}

@Composable
private fun Content(
    state: MainViewState,
    onDeleteClick: (Int) -> Unit,
    onCardClick: (Int) -> Unit,
) {
    if (state.lists.isEmpty())
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
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
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.lists, key = { item -> item.id }) { list ->
                ShoppingListCard(
                    name = list.name,
                    onDeleteClick = { onDeleteClick(list.id) },
                    onCardClick = { onCardClick(list.id) }
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
                contentDescription = ShlistResources.strings.newList,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(MediumPadding))
            Text(
                text = ShlistResources.strings.newList,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CreateListDialog(
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
                    placeholder = { Text(ShlistResources.strings.newList) },
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Button(
                    onClick = { onCreateListClick(tfValue) }
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
private fun ShoppingListCard(
    modifier: Modifier = Modifier,
    name: String,
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
                style = MaterialTheme.typography.bodyLarge,
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