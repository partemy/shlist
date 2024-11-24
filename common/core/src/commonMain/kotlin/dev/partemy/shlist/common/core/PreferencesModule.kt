package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.preferences.PreferencesFactory
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferencesFactory.createPreferences() }
}