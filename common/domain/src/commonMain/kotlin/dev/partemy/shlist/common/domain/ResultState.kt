package dev.partemy.shlist.common.domain

sealed class ResultState<out T : Any>(open val data: T? = null) {
    class Success<T : Any>(
        override val data: T
    ) : ResultState<T>(data)

    class Failure<T : Any>(
        data: T?,
        val exception: Exception? = null,
    ) : ResultState<T>(data)

    class Loading<T : Any>(
        data: T?
    ) : ResultState<T>(data)
}