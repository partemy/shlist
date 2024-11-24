package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.shlist.api.api.AuthService
import dev.partemy.shlist.common.shlist.api.api.IAuthService
import dev.partemy.shlist.common.shlist.api.api.IShoppingListService
import dev.partemy.shlist.common.shlist.api.api.ShoppingListService
import dev.partemy.shlist.common.shlist.api.createHttpClient
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteModule = module {
    single<HttpClient> { createHttpClient() }
    singleOf(::ShoppingListService).bind(IShoppingListService::class)
    singleOf(::AuthService).bind(IAuthService::class)
}