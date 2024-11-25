package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository

class CrossOutShoppingListItemUseCase(
    private val repository: IShoppingListItemsRepository
) {
    suspend operator fun invoke(itemId: Int, listId: Int) =
        repository.crossOutShoppingListItem(itemId, listId)
}