package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.domain.usecase.CreateShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import dev.partemy.shlist.common.domain.usecase.GetShoppingListItemsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetAllShoppingListsUseCase)
    singleOf(::CreateShoppingListUseCase)
    singleOf(::DeleteShoppingListUseCase)
    singleOf(::GetShoppingListItemsUseCase)
}