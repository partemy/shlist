package dev.partemy.shlist.feature.shoppinglist.di

import dev.partemy.shlist.feature.shoppinglist.ui.ShoppingListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val shoppingListViewModelModule = module {
    factoryOf(::ShoppingListViewModel)
}