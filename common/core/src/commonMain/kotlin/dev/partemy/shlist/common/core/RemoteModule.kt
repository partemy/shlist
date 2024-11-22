package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.data.remote.api.AuthService
import dev.partemy.shlist.common.data.remote.api.ShoppingListService
import dev.partemy.shlist.common.data.remote.api.impl.AuthServiceImpl
import dev.partemy.shlist.common.data.remote.api.impl.ShoppingListServiceImpl
import dev.partemy.shlist.common.data.remote.createHttpClient
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteModule = module {
    single<HttpClient> { createHttpClient() }
    singleOf(::ShoppingListServiceImpl).bind(ShoppingListService::class)
    singleOf(::AuthServiceImpl).bind(AuthService::class)
}