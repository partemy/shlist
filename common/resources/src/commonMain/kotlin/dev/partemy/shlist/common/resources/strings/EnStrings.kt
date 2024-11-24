package dev.partemy.shlist.common.resources.strings

import cafe.adriel.lyricist.LyricistStrings
import dev.partemy.shlist.common.resources.Locales

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnStrings = ShlistStrings(
    newList = "New list",
    create = "Create",
    noLists = "No lists",
    newItem = "New item",
    noItems = "No items",
)