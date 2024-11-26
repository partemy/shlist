package dev.partemy.shlist.di

import dev.partemy.shlist.feature.auth.di.authViewModelModule
import dev.partemy.shlist.feature.main.di.mainViewModelModule
import dev.partemy.shlist.feature.shoppinglist.di.shoppingListViewModelModule

val presentationModule = mainViewModelModule + shoppingListViewModelModule + authViewModelModule