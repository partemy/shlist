package dev.partemy.shlist.feature.main.di

import dev.partemy.shlist.feature.main.ui.MainViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainViewModelModule = module {
    factoryOf(::MainViewModel)
}