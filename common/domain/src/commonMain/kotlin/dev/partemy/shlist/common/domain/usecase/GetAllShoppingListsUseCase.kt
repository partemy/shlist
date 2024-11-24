package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IShoppingListRepository

class GetAllShoppingListsUseCase(
    private val repository: IShoppingListRepository,
) {
    suspend operator fun invoke(key: String) = repository.getAllShoppingLists()
}