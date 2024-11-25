package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository

class AddShoppingListItemUseCase(
    private val repository: IShoppingListItemsRepository
) {
    suspend operator fun invoke(listId: Int, name: String, count: Int) =
        repository.createShoppingListItem(listId, name, count)
}