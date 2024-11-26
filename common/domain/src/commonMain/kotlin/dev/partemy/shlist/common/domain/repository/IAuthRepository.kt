package dev.partemy.shlist.common.domain.repository

import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun createKey() : Result<Nothing?>
    suspend fun passKey(key: String) : Result<Nothing?>
    suspend fun deleteKey() : Result<Nothing?>
    fun getKey() : Flow<String?>
}