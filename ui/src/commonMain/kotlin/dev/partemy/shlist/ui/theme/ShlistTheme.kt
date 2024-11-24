package dev.partemy.shlist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import dev.partemy.shlist.ui.theme.ShlistColors.backgroundDark
import dev.partemy.shlist.ui.theme.ShlistColors.backgroundLight
import dev.partemy.shlist.ui.theme.ShlistColors.errorContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.errorContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.errorDark
import dev.partemy.shlist.ui.theme.ShlistColors.scrimDark
import dev.partemy.shlist.ui.theme.ShlistColors.errorLight
import dev.partemy.shlist.ui.theme.ShlistColors.inverseOnSurfaceDark
import dev.partemy.shlist.ui.theme.ShlistColors.inverseOnSurfaceLight
import dev.partemy.shlist.ui.theme.ShlistColors.inversePrimaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.inversePrimaryLight
import dev.partemy.shlist.ui.theme.ShlistColors.inverseSurfaceDark
import dev.partemy.shlist.ui.theme.ShlistColors.inverseSurfaceLight
import dev.partemy.shlist.ui.theme.ShlistColors.onBackgroundDark
import dev.partemy.shlist.ui.theme.ShlistColors.onBackgroundLight
import dev.partemy.shlist.ui.theme.ShlistColors.onErrorContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.onErrorContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.onErrorDark
import dev.partemy.shlist.ui.theme.ShlistColors.onErrorLight
import dev.partemy.shlist.ui.theme.ShlistColors.onPrimaryContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.onPrimaryContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.onPrimaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.onPrimaryLight
import dev.partemy.shlist.ui.theme.ShlistColors.onSecondaryContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.onSecondaryContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.onSecondaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.onSecondaryLight
import dev.partemy.shlist.ui.theme.ShlistColors.onSurfaceDark
import dev.partemy.shlist.ui.theme.ShlistColors.onSurfaceLight
import dev.partemy.shlist.ui.theme.ShlistColors.onSurfaceVariantDark
import dev.partemy.shlist.ui.theme.ShlistColors.onSurfaceVariantLight
import dev.partemy.shlist.ui.theme.ShlistColors.onTertiaryContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.onTertiaryContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.onTertiaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.onTertiaryLight
import dev.partemy.shlist.ui.theme.ShlistColors.outlineDark
import dev.partemy.shlist.ui.theme.ShlistColors.outlineLight
import dev.partemy.shlist.ui.theme.ShlistColors.outlineVariantDark
import dev.partemy.shlist.ui.theme.ShlistColors.outlineVariantLight
import dev.partemy.shlist.ui.theme.ShlistColors.primaryContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.primaryContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.primaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.primaryLight
import dev.partemy.shlist.ui.theme.ShlistColors.scrimLight
import dev.partemy.shlist.ui.theme.ShlistColors.secondaryContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.secondaryContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.secondaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.secondaryLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceBrightDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceBrightLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerHighDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerHighLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerHighestDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerHighestLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerLowDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerLowLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerLowestDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceContainerLowestLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceDimDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceDimLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceLight
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceVariantDark
import dev.partemy.shlist.ui.theme.ShlistColors.surfaceVariantLight
import dev.partemy.shlist.ui.theme.ShlistColors.tertiaryContainerDark
import dev.partemy.shlist.ui.theme.ShlistColors.tertiaryContainerLight
import dev.partemy.shlist.ui.theme.ShlistColors.tertiaryDark
import dev.partemy.shlist.ui.theme.ShlistColors.tertiaryLight

val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Composable
fun ShlistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colorScheme = if (darkTheme) darkScheme else lightScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography.typography,
        shapes = shapes,
        content = content
    )
}

