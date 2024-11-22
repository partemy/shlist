package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.domain.usecase.GetAllShoppingListsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetAllShoppingListsUseCase(get()) }
}