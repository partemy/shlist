package dev.partemy.shlist.common.data.remote

import dev.partemy.shlist.common.domain.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

suspend fun <T : Any> apiCall(apiCall: suspend () -> T): Flow<ResultState<T>> {
    return flowOf(
        try {
            ResultState.Success(apiCall.invoke())
        } catch (e: Exception) {
            ResultState.Failure(exception = e, data = null)
        }
    )
        .flowOn(Dispatchers.IO)
        .onStart { ResultState.Loading(data = null) }
}
