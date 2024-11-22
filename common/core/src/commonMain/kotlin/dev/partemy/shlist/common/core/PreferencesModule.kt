package dev.partemy.shlist.common.core

import org.koin.dsl.module

val preferencesModule = module {
    single { PreferencesFactory.createPreferences() }
}