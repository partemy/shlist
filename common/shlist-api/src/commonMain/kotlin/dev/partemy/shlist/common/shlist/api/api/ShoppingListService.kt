package dev.partemy.shlist.common.shlist.api.api

import dev.partemy.shlist.common.shlist.api.util.apiCall
import dev.partemy.shlist.common.shlist.api.model.AddListItemResult
import dev.partemy.shlist.common.shlist.api.model.CreateListResult
import dev.partemy.shlist.common.shlist.api.model.CrossOffListItemResult
import dev.partemy.shlist.common.shlist.api.model.GetAllListsResult
import dev.partemy.shlist.common.shlist.api.model.GetListResult
import dev.partemy.shlist.common.shlist.api.model.RemoveListItemResult
import dev.partemy.shlist.common.shlist.api.model.RemoveListResult
import dev.partemy.shlist.common.shlist.api.util.ApiRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class ShoppingListService(
    private val httpClient: HttpClient,
) : IShoppingListService {

    override suspend fun createShoppingList(
        key: String,
        name: String,
    ): Result<CreateListResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.CREATE_LIST) {
            parameter(key = "key", value = key)
            parameter(key = "name", value = name)
        }.body<CreateListResult>()
    }

    override suspend fun removeShoppingList(
        listId: Int,
    ): Result<RemoveListResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.REMOVE_LIST) {
            parameter(key = "list_id", value = listId)
        }.body<RemoveListResult>()
    }

    override suspend fun addToShoppingList(
        listId: Int,
        name: String,
        count: Int,
    ): Result<AddListItemResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.ADD_TO_LIST) {
            parameter(key = "id", value = listId)
            parameter(key = "value", value = name)
            parameter(key = "n", value = count)
        }.body<AddListItemResult>()
    }

    override suspend fun removeFromShoppingList(
        listId: Int,
        itemId: Int,
    ): Result<RemoveListItemResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.REMOVE_FROM_LIST) {
            parameter(key = "list_id", value = listId)
            parameter(key = "item_id", value = itemId)
        }.body<RemoveListItemResult>()
    }

    override suspend fun crossOffItem(
        itemId: Int,
    ): Result<CrossOffListItemResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.CROSS_OFF) {
            parameter(key = "id", value = itemId)
        }.body<CrossOffListItemResult>()
    }

    override suspend fun getAllShoppingLists(
        key: String,
    ): Result<GetAllListsResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.GET_ALL_LISTS) {
            parameter(key = "key", value = key)
        }.body<GetAllListsResult>()
    }

    override suspend fun getShoppingList(
        listId: Int,
    ): Result<GetListResult> = apiCall {
        httpClient.post(urlString = ApiRoutes.GET_LIST_ITEMS) {
            parameter(key = "list_id", value = listId)
        }.body<GetListResult>()
    }
}