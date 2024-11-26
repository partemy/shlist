package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.data.local.AuthLocalDataSource
import dev.partemy.shlist.common.data.local.IAuthLocalDataSource
import dev.partemy.shlist.common.data.remote.AuthRemoteDataSource
import dev.partemy.shlist.common.data.remote.IAuthRemoteDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.data.remote.ShoppingListRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteDataSourceModule = module {
    singleOf(::ShoppingListRemoteDataSource).bind(IShoppingListRemoteDataSource::class)
    singleOf(::AuthRemoteDataSource).bind(IAuthRemoteDataSource::class)
}