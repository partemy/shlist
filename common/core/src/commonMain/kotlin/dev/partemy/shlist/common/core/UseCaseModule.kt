package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.domain.usecase.AddShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.CreateKeyUseCase
import dev.partemy.shlist.common.domain.usecase.CreateShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.CrossOutShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteKeyUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListItemUseCase
import dev.partemy.shlist.common.domain.usecase.DeleteShoppingListUseCase
import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import dev.partemy.shlist.common.domain.usecase.GetKeyUseCase
import dev.partemy.shlist.common.domain.usecase.GetShoppingListItemsUseCase
import dev.partemy.shlist.common.domain.usecase.VerifyKeyUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetAllShoppingListsUseCase)
    singleOf(::CreateShoppingListUseCase)
    singleOf(::DeleteShoppingListUseCase)
    singleOf(::GetShoppingListItemsUseCase)
    singleOf(::AddShoppingListItemUseCase)
    singleOf(::DeleteShoppingListItemUseCase)
    singleOf(::CrossOutShoppingListItemUseCase)
    singleOf(::CreateKeyUseCase)
    singleOf(::VerifyKeyUseCase)
    singleOf(::CreateKeyUseCase)
    singleOf(::GetKeyUseCase)
}