package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.data.repository.ShoppingListItemsRepository
import dev.partemy.shlist.common.data.repository.ShoppingListRepository
import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository
import dev.partemy.shlist.common.domain.repository.IShoppingListRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ShoppingListRepository).bind(IShoppingListRepository::class)
    singleOf(::ShoppingListItemsRepository).bind(IShoppingListItemsRepository::class)
}