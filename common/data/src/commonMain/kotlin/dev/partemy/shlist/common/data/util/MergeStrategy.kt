package dev.partemy.shlist.common.data.util

import dev.partemy.shlist.common.domain.ResultState
import dev.partemy.shlist.common.domain.ResultState.Loading
import dev.partemy.shlist.common.domain.ResultState.Failure
import dev.partemy.shlist.common.domain.ResultState.Success

interface MergeStrategy<T> {
    fun merge(
        right: T,
        left: T
    ): T
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<ResultState<T>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(
        right: ResultState<T>,
        left: ResultState<T>
    ): ResultState<T> {
        return when {
            right is Loading && left is Loading -> merge(right, left)
            right is Success && left is Loading -> merge(right, left)
            right is Loading && left is Success -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is Success && left is Failure -> merge(right, left)
            right is Loading && left is Failure -> merge(right, left)
            right is Failure && left is Loading -> merge(right, left)
            right is Failure && left is Success -> merge(right, left)

            else -> error("Unimplemented branch right=$right & left=$left")
        }
    }

    private fun merge(
        cache: Loading<T>,
        server: Loading<T>
    ): ResultState<T> {
        return when {
            server.data != null -> Loading(server.data)
            else -> Loading(cache.data)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: Loading<T>
    ): ResultState<T> {
        return Loading(cache.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Loading<T>,
        server: Success<T>
    ): ResultState<T> {
        return Loading(server.data)
    }

    private fun merge(
        cache: Success<T>,
        server: Failure<T>
    ): ResultState<T> {
        return Failure(data = cache.data, exception = server.exception)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: Success<T>
    ): ResultState<T> {
        return Success(data = server.data)
    }

    private fun merge(
        cache: Loading<T>,
        server: Failure<T>
    ): ResultState<T> {
        return Failure(data = cache.data, exception = server.exception)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Failure<T>,
        server: Loading<T>
    ): ResultState<T> {
        return server
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Failure<T>,
        server: Success<T>
    ): ResultState<T> {
        return server
    }
}