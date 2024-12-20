package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.data.remote.impl.AuthRemoteDataSource
import dev.partemy.shlist.common.data.remote.IAuthRemoteDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.data.remote.impl.ShoppingListRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteDataSourceModule = module {
    singleOf(::ShoppingListRemoteDataSource).bind(IShoppingListRemoteDataSource::class)
    singleOf(::AuthRemoteDataSource).bind(IAuthRemoteDataSource::class)
}