package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.data.local.AuthLocalDataSource
import dev.partemy.shlist.common.data.local.IAuthLocalDataSource
import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.local.ShoppingListLocalDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localDataSourceModule = module {
    singleOf(::ShoppingListLocalDataSource).bind(IShoppingListLocalDataSource::class)
    singleOf(::AuthLocalDataSource).bind(IAuthLocalDataSource::class)
}