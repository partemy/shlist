package dev.partemy.shlist.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.partemy.shlist.common.resources.ShlistResources
import dev.partemy.shlist.ui.values.MediumPadding
import org.jetbrains.compose.resources.vectorResource
import shlist.common.resources.generated.resources.plus

@Composable
fun ShlistFloatingButton(
    modifier: Modifier = Modifier,
    onActionClick: (String) -> Unit,
    text: String,
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
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}