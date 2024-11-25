package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository

class DeleteShoppingListItemUseCase(
    private val repository: IShoppingListItemsRepository
) {
    suspend operator fun invoke(itemId: Int, listId: Int) =
        repository.deleteShoppingListItem(itemId, listId)
}