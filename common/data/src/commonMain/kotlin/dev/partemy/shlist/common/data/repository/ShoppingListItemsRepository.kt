package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.data.util.MergeStrategy
import dev.partemy.shlist.common.data.util.RequestResponseMergeStrategy
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import dev.partemy.shlist.common.domain.repository.IShoppingListItemsRepository
import dev.partemy.shlist.common.domain.toResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class ShoppingListItemsRepository(
    private val shoppingListRemoteDataSource: IShoppingListRemoteDataSource,
    private val shoppingListLocalDataSource: IShoppingListLocalDataSource
) : IShoppingListItemsRepository {

    private val _listItemsFlow = MutableSharedFlow<ResultState<List<ShoppingListItem>>>(replay = 1)
    private val listItemsFlow = _listItemsFlow.asSharedFlow()
    private var syncJob: Job? = null

    override fun getAllShoppingListItems(listId: Int): Flow<ResultState<List<ShoppingListItem>>> {
        syncJob?.cancel()
        val cachedListItems = getListItemsFromDatabase(listId)
        val remoteListItems = listItemsFlow
        val mergeStrategy: MergeStrategy<ResultState<List<ShoppingListItem>>> =
            RequestResponseMergeStrategy()
        syncJob = CoroutineScope(Dispatchers.IO).launch {
            schedulePeriodicSync(listId)
        }
        return cachedListItems.combine(remoteListItems, mergeStrategy::merge)
    }

    override suspend fun createShoppingListItem(
        listId: Int,
        name: String,
        count: Int
    ): Result<Int> {
        val apiRequest = shoppingListRemoteDataSource.addToShoppingList(listId, name, count)
        return if (apiRequest.isSuccess) {
            syncWithServer(listId)
            Result.success(apiRequest.getOrThrow())
        } else Result.failure(apiRequest.exceptionOrNull() ?: Exception())
    }

    override suspend fun deleteShoppingListItem(itemId: Int, listId: Int): Result<Nothing?> {
        val apiRequest = shoppingListRemoteDataSource.removeFromShoppingList(listId, itemId)
        return if (apiRequest.isSuccess) {
            syncWithServer(listId)
            Result.success(null)
        } else Result.failure(apiRequest.exceptionOrNull() ?: Exception())
    }

    override suspend fun crossOutShoppingListItem(itemId: Int, listId: Int): Result<Nothing?> {
        val apiRequest = shoppingListRemoteDataSource.crossOffItem(itemId)
        return if (apiRequest.isSuccess) {
            syncWithServer(listId)
            Result.success(null)
        } else Result.failure(apiRequest.exceptionOrNull() ?: Exception())
    }


    private suspend fun syncWithServer(listId: Int) {
        _listItemsFlow.emitAll(
            getListItemsFromServer(listId)
                .onStart { emit(ResultState.Loading()) }
                .catch { emit(ResultState.Failure(exception = Exception(it))) }
        )
    }

    private suspend fun schedulePeriodicSync(
        listId: Int,
        intervalMs: Long = 5000L,
    ) {
        while (coroutineContext.isActive) {
            _listItemsFlow.emitAll(getListItemsFromServer(listId))
            delay(intervalMs)
        }
    }

    private fun getListItemsFromServer(listId: Int): Flow<ResultState<List<ShoppingListItem>>> {
        val apiRequest = flow {
            emit(shoppingListRemoteDataSource.getShoppingList(listId = listId))
        }
            .onEach { result ->
                if (result.isSuccess) {
                    val data = shoppingListLocalDataSource.getAllListItems(listId)
                    result.getOrThrow().let {
                        if (data != it) shoppingListLocalDataSource.replaceListItems(it, listId)
                    }
                }
            }
            .map { it.toResultState() }
            .catch { emit(ResultState.Failure(exception = Exception(it))) }
        return apiRequest
    }

    private fun getListItemsFromDatabase(listId: Int) =
        shoppingListLocalDataSource.getAllListItems(listId = listId)
            .map<List<ShoppingListItem>, ResultState<List<ShoppingListItem>>> {
                ResultState.Success(it)
            }
            .onStart { ResultState.Loading(null) }
            .catch {
                ResultState.Failure(
                    data = null,
                    exception = Exception("Error: get items from db")
                )
            }
}