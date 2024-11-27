package dev.partemy.shlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.values.SmallPadding

@Composable
fun OfflineIndicator(modifier: Modifier = Modifier) {
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