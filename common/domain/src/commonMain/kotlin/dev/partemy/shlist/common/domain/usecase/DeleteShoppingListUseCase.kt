package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListRepository

class DeleteShoppingListUseCase(
    private val repository: IShoppingListRepository
) {
    suspend operator fun invoke(listId: Int) = repository.deleteShoppingList(listId)
}