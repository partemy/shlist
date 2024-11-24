package dev.partemy.shlist.common.shlist.api.util

suspend fun <T : Any> apiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.success(apiCall.invoke())
    } catch (e: Exception) {
        Result.failure(exception = e)
    }
}
