package dev.partemy.shlist.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import cafe.adriel.lyricist.LocalStrings
import dev.partemy.shlist.common.resources.strings.ShlistStrings
import shlist.common.resources.generated.resources.Res

object ShlistResources {
    val strings: ShlistStrings
        @Composable
        @ReadOnlyComposable
        get() = LocalStrings.current
    val drawable: Res.drawable
        @Composable
        @ReadOnlyComposable
        get() = Res.drawable
}