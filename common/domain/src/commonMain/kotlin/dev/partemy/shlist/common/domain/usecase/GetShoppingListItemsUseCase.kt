package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository

class GetShoppingListItemsUseCase(
    private val repository: IShoppingListItemsRepository
) {
    operator fun invoke(listId: Int) = repository.getAllShoppingListItems(listId)
}