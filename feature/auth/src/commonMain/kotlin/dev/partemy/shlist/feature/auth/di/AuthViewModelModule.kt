package dev.partemy.shlist.feature.auth.di

import dev.partemy.shlist.feature.auth.ui.AuthViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authViewModelModule = module {
    factoryOf(::AuthViewModel)
}