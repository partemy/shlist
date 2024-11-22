package dev.partemy.shlist.common.data.local

import kotlinx.coroutines.flow.Flow

interface IAuthLocalDataSource {
    suspend fun setKey(key: String)
    fun getKey(): Flow<String?>
}