package dev.partemy.shlist.common.resources

import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import dev.partemy.shlist.common.resources.strings.EnStrings
import dev.partemy.shlist.common.resources.strings.RuStrings
import dev.partemy.shlist.common.resources.strings.ShlistStrings

internal val ShlistAppStrings: Map<String, ShlistStrings> = mapOf(
    Locales.EN to EnStrings,
    Locales.RU to RuStrings,
)

@Composable
internal fun rememberShlistStrings(): Lyricist<ShlistStrings> =
    rememberStrings(ShlistAppStrings)

@Composable
fun ProvideShlistStrings(
    content: @Composable () -> Unit
) {
    ProvideStrings(rememberShlistStrings(), LocalStrings, content)
}