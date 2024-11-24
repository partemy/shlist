package dev.partemy.shlist.common.domain

sealed class ResultState<out T : Any>(open val data: T? = null) {
    class Success<T : Any>(
        override val data: T
    ) : ResultState<T>(data)

    class Failure<T : Any>(
        data: T? = null,
        val exception: Exception? = null,
    ) : ResultState<T>(data)

    class Loading<T : Any>(
        data: T? = null
    ) : ResultState<T>(data)
}

fun <T : Any> Result<T>.toResultState(): ResultState<T> {
    return when {
        isSuccess -> ResultState.Success(getOrThrow())
        isFailure -> ResultState.Failure(exception = Exception("${this.exceptionOrNull()}"))
        else -> error("Impossible branch")
    }
}