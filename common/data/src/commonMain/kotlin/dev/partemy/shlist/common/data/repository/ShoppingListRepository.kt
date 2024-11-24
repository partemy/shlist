package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.data.util.MergeStrategy
import dev.partemy.shlist.common.data.util.RequestResponseMergeStrategy
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.repository.IShoppingListRepository
import dev.partemy.shlist.common.domain.toResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class ShoppingListRepository(
    private val shoppingListRemoteDataSource: IShoppingListRemoteDataSource,
    private val shoppingListLocalDataSource: IShoppingListLocalDataSource
) : IShoppingListRepository {

    private val _shoppingListsFlow =
        MutableSharedFlow<ResultState<List<ShoppingList>>>(replay = 1)
    private val shoppingListFlow = _shoppingListsFlow.asSharedFlow()

    override fun getAllShoppingLists(
        key: String,
    ): Flow<ResultState<List<ShoppingList>>> {

        val cachedShoppingLists = getAllListsFromDataBase()
        val remoteShoppingLists = shoppingListFlow
        val mergeStrategy: MergeStrategy<ResultState<List<ShoppingList>>> =
            RequestResponseMergeStrategy()
        CoroutineScope(Dispatchers.IO).launch {
            schedulePeriodicSync(key)
        }
        return cachedShoppingLists.combine(remoteShoppingLists, mergeStrategy::merge)
    }

    override suspend fun createShoppingList(key: String, name: String): Result<Nothing?> {
        val apiResult = shoppingListRemoteDataSource.createShoppingList(key, name)
        return if (apiResult.isSuccess) {
            syncWithServer(key)
            val newList = ShoppingList(name = name, id = apiResult.getOrThrow(), created = "")
            shoppingListLocalDataSource.insertList(newList)
            Result.success(null)
        } else {
            Result.failure(Exception("Failed to create shopping list on server."))
        }
    }

    override suspend fun deleteShoppingList(key: String, listId: Int): Result<Nothing?> {
        val apiResult = shoppingListRemoteDataSource.deleteShoppingList(listId)
        return if (apiResult.isSuccess) {
            syncWithServer(key)
            shoppingListLocalDataSource.deleteList(listId)
            Result.success(null)
        } else {
            Result.failure(Exception("Failed to delete shopping list"))
        }
    }

    private suspend fun syncWithServer(key: String) {
        _shoppingListsFlow.emitAll(
            getAllListsFromServer(key)
                .onStart { emit(ResultState.Loading()) }
                .catch { emit(ResultState.Failure(exception = Exception(it))) }
        )
    }

    private suspend fun schedulePeriodicSync(
        key: String,
        intervalMs: Long = 5000L,
    ) {
        while (coroutineContext.isActive) {
            _shoppingListsFlow.emitAll(getAllListsFromServer(key))
            delay(intervalMs)
        }
    }

    private fun getAllListsFromServer(key: String): Flow<ResultState<List<ShoppingList>>> {
        val apiRequest = flow {
            emit(
                shoppingListRemoteDataSource.getAllShoppingLists(key)
            )
        }
            .onEach { result ->
                if (result.isSuccess) {
                    val currentData = shoppingListLocalDataSource.getAllLists().first()
                    result.getOrThrow()
                        .let { if (it != currentData) shoppingListLocalDataSource.replaceAllLists(it) }
                }
            }
            .map { it.toResultState() }
            .catch { emit(ResultState.Failure()) }
        return apiRequest
    }

    private fun getAllListsFromDataBase(): Flow<ResultState<List<ShoppingList>>> =
        shoppingListLocalDataSource.getAllLists()
            .map<List<ShoppingList>, ResultState<List<ShoppingList>>> { ResultState.Success(it) }
            .onStart { ResultState.Loading(data = null) }
            .catch {
                ResultState.Failure(
                    exception = Exception("error: get lists from db"),
                    data = null
                )
            }
}
