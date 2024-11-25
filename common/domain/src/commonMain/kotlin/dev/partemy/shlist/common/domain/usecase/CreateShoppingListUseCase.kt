package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListRepository

class CreateShoppingListUseCase(
    private val repository: IShoppingListRepository
) {
    suspend operator fun invoke(name: String) = repository.createShoppingList(name)
}