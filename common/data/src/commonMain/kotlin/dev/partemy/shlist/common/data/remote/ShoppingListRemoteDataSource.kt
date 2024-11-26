package dev.partemy.shlist.common.data.remote

import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.shlist.api.api.IShoppingListService
import dev.partemy.shlist.common.shlist.api.mapper.toShoppingListItems
import dev.partemy.shlist.common.shlist.api.mapper.toShoppingLists

class ShoppingListRemoteDataSource(
    private val shoppingListService: IShoppingListService
) : IShoppingListRemoteDataSource {

    override suspend fun getAllShoppingLists(key: String): Result<List<ShoppingList>> {
        val request = shoppingListService.getAllShoppingLists(key)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else request.map { it.shop_list.toShoppingLists() }
    }

    override suspend fun getShoppingList(listId: Int): Result<List<ShoppingListItem>> {
        val request = shoppingListService.getShoppingList(listId)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else request.map { it.item_list.toShoppingListItems(listId) }
    }

    override suspend fun createShoppingList(key: String, name: String): Result<Int> {
        val request = shoppingListService.createShoppingList(key, name)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else request.map { it.list_id }
    }

    override suspend fun deleteShoppingList(listId: Int): Result<Boolean> {
        val request = shoppingListService.removeShoppingList(listId)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else request.map { it.new_value }
    }

    override suspend fun addToShoppingList(listId: Int, name: String, count: Int): Result<Int> {
        val request = shoppingListService.addToShoppingList(listId, name, count)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else request.map { it.item_id }
    }

    override suspend fun removeFromShoppingList(listId: Int, itemId: Int): Result<Nothing?> {
        val request = shoppingListService.removeFromShoppingList(listId, itemId)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else Result.success(null)
    }

    override suspend fun crossOffItem(itemId: Int): Result<Int> {
        val request = shoppingListService.crossOffItem(itemId)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            exception = Exception(request.exceptionOrNull())
        ) else request.map { it.rows_affected }
    }

}