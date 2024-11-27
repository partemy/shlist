package dev.partemy.shlist.common.data.local

import kotlinx.coroutines.flow.Flow

interface IAuthLocalDataSource {
    suspend fun setKey(key: String)
    suspend fun clearAll()
    fun getKey(): Flow<String?>
}