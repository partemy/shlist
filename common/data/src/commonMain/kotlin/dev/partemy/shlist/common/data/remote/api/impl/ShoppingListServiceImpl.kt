package dev.partemy.shlist.common.data.remote.api.impl

import dev.partemy.shlist.common.data.ResultState
import dev.partemy.shlist.common.data.remote.ApiRoutes
import dev.partemy.shlist.common.data.remote.api.ShoppingListService
import dev.partemy.shlist.common.data.remote.apiCall
import dev.partemy.shlist.common.data.remote.model.AddListItemResult
import dev.partemy.shlist.common.data.remote.model.CreateListResult
import dev.partemy.shlist.common.data.remote.model.CrossOffListItemResult
import dev.partemy.shlist.common.data.remote.model.GetAllListsResult
import dev.partemy.shlist.common.data.remote.model.GetListResult
import dev.partemy.shlist.common.data.remote.model.RemoveListItemResult
import dev.partemy.shlist.common.data.remote.model.RemoveListResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ShoppingListServiceImpl(
    private val httpClient: HttpClient,
) : ShoppingListService {

    override suspend fun createShoppingList(
        key: String,
        name: String,
    ): Flow<ResultState<CreateListResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.CREATE_LIST) {
                    parameter(key = "key", value = key)
                    parameter(key = "name", value = name)
                }.body<CreateListResult>()
            }
        )

    override suspend fun removeShoppingList(listId: Int): Flow<ResultState<RemoveListResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.REMOVE_LIST) {
                    parameter(key = "list_id", value = listId)
                }.body<RemoveListResult>()
            }
        )

    override suspend fun addToShoppingList(
        listId: Int,
        name: String,
        count: Int
    ): Flow<ResultState<AddListItemResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.ADD_TO_LIST) {
                    parameter(key = "id", value = listId)
                    parameter(key = "value", value = name)
                    parameter(key = "n", value = count)
                }.body<AddListItemResult>()
            }
        )


    override suspend fun removeFromShoppingList(
        listId: Int,
        itemId: Int
    ): Flow<ResultState<RemoveListItemResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.REMOVE_FROM_LIST) {
                    parameter(key = "list_id", value = listId)
                    parameter(key = "item_id", value = itemId)
                }.body<RemoveListItemResult>()
            }
        )

    override suspend fun crossOffItem(itemId: Int): Flow<ResultState<CrossOffListItemResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.CROSS_OFF) {
                    parameter(key = "item_id", value = itemId)
                }.body<CrossOffListItemResult>()
            }
        )

    override suspend fun getAllShoppingLists(key: String): Flow<ResultState<GetAllListsResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.GET_ALL_LISTS) {
                    parameter(key = "key", value = key)
                }.body<GetAllListsResult>()
            }
        )

    override suspend fun getShoppingList(listId: Int): Flow<ResultState<GetListResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.GET_LIST_ITEMS) {
                    parameter(key = "list_id", value = listId)
                }.body<GetListResult>()
            }
        )
}