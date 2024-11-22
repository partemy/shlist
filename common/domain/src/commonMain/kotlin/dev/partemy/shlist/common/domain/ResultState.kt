package dev.partemy.shlist.common.domain

sealed class ResultState<out T> {
    data class Success<out T : Any?>(val data: T) : ResultState<T>()
    data class Failure(val exception: Exception) : ResultState<Nothing>()
    class Loading : ResultState<Nothing>()
}