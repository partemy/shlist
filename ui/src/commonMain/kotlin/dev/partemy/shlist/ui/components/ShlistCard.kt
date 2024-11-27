package dev.partemy.shlist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.values.SmallPadding
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import shlist.common.resources.generated.resources.delete


@Composable
fun ShlistCard(
    modifier: Modifier = Modifier,
    name: String,
    icon: DrawableResource = ShlistResources.drawable.delete,
    isCrossed: Boolean,
    onIconClick: () -> Unit,
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
                vectorResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = { onIconClick.invoke() })
            )
        }
    }
}