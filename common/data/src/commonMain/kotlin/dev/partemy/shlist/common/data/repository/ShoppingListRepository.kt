package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.local.IShoppingListLocalDataSource
import dev.partemy.shlist.common.data.remote.IShoppingListRemoteDataSource
import dev.partemy.shlist.common.data.util.MergeStrategy
import dev.partemy.shlist.common.data.util.RequestResponseMergeStrategy
import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.repository.IShoppingListRepository
import dev.partemy.shlist.common.domain.toResultState
import dev.partemy.shlist.common.preferences.ShlistPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ShoppingListRepository(
    private val shoppingListRemoteDataSource: IShoppingListRemoteDataSource,
    private val shoppingListLocalDataSource: IShoppingListLocalDataSource,
    private val preferences: ShlistPreferences,
) : IShoppingListRepository {

    private val _shoppingListsFlow =
        MutableStateFlow<ResultState<List<ShoppingList>>>(ResultState.Loading())
    private val shoppingListFlow = _shoppingListsFlow.asSharedFlow()
    private val key = preferences.getString("key")
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var syncJob: Job? = null

    override suspend fun getAllShoppingLists(): Flow<ResultState<List<ShoppingList>>> {
        syncJob?.cancelAndJoin()
        _shoppingListsFlow.value = ResultState.Loading()

        val key = key.first()
            ?: return flow { ResultState.Failure<List<ShoppingList>>(exception = Exception("no key")) }
        val cachedShoppingLists = getAllListsFromDataBase()
        val remoteShoppingLists = shoppingListFlow
        val mergeStrategy: MergeStrategy<ResultState<List<ShoppingList>>> =
            RequestResponseMergeStrategy()

        schedulePeriodicSync(key)

        return cachedShoppingLists.combine(remoteShoppingLists, mergeStrategy::merge)
    }

    override suspend fun createShoppingList(name: String): Result<Nothing?> {
        val key = key.first()
            ?: return Result.failure(exception = Exception("no key"))
        val apiResult = shoppingListRemoteDataSource.createShoppingList(key, name)
        return if (apiResult.isSuccess) {
            syncWithServer(key)
            val newList = ShoppingList(name = name, id = apiResult.getOrThrow(), created = "")
            shoppingListLocalDataSource.insertList(newList)
            Result.success(null)
        } else {
            Result.failure(apiResult.exceptionOrNull() ?: Exception())
        }
    }

    override suspend fun deleteShoppingList(listId: Int): Result<Nothing?> {
        val key = key.first()
            ?: return Result.failure(exception = Exception("no key"))
        val apiResult = shoppingListRemoteDataSource.deleteShoppingList(listId)
        return if (apiResult.isSuccess) {
            syncWithServer(key)
            shoppingListLocalDataSource.deleteList(listId)
            Result.success(null)
        } else {
            Result.failure(apiResult.exceptionOrNull() ?: Exception())
        }
    }

    private suspend fun syncWithServer(key: String) {
        try {
            getAllListsFromServer(key)
                .onStart { _shoppingListsFlow.value = ResultState.Loading() }
                .collect { result ->
                    _shoppingListsFlow.value = result
                }
        } catch (e: Exception) {
            _shoppingListsFlow.value = ResultState.Failure(exception = e)
        }
    }

    private fun schedulePeriodicSync(
        key: String,
        intervalMs: Long = 5000L,
    ) {
        syncJob?.cancel()
        syncJob = coroutineScope.launch {
            while (isActive) {
                try {
                    getAllListsFromServer(key).collect { result ->
                        _shoppingListsFlow.value = result
                    }
                } catch (e: Exception) {
                    _shoppingListsFlow.value = ResultState.Failure(exception = e)
                }
                delay(intervalMs)
            }
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
            .onStart { emit(ResultState.Loading(data = null)) }
            .catch {
                ResultState.Failure(
                    exception = Exception("error: get lists from db"),
                    data = null
                )
            }
}
