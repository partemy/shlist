package dev.partemy.shlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import dev.partemy.shlist.ui.values.MediumPadding
import dev.partemy.shlist.ui.values.SmallPadding

@Composable
fun ShlistCreationDialog(
    onDismissRequest: () -> Unit,
    onCreateListClick: (String) -> Unit,
    labelText: String,
    buttonText: String,
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
                    placeholder = { Text(labelText) },
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Button(
                    onClick = { onCreateListClick(tfValue); onDismissRequest() }
                ) {
                    Text(
                        buttonText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}