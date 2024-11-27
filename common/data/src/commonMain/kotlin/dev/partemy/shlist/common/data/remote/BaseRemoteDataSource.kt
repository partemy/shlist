package dev.partemy.shlist.common.data.remote

abstract class BaseRemoteDataSource {
    inline fun <T, O> handleRequest(request: Result<T>, transform: (T) -> O): Result<O> {
        return if (request.isFailure) {
            Result.failure(exception = request.exceptionOrNull() ?: Exception("Unknown error"))
        } else {
            request.getOrNull()?.let { Result.success(transform(it)) }
                ?: Result.failure(Exception("Null response"))
        }
    }
}