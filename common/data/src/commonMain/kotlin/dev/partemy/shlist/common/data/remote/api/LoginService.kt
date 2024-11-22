package dev.partemy.shlist.common.data.remote.api

import dev.partemy.shlist.common.data.ResultState
import dev.partemy.shlist.common.data.remote.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface LoginService {
    suspend fun createKey(): Flow<ResultState<String>>
    suspend fun authByKey(key: String): Flow<ResultState<AuthResult>>
}