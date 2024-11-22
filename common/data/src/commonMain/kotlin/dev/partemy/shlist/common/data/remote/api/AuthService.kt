package dev.partemy.shlist.common.data.remote.api

import dev.partemy.shlist.common.data.remote.model.AuthResult
import dev.partemy.shlist.common.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthService {
    suspend fun createKey(): Flow<ResultState<String>>
    suspend fun authByKey(key: String): Flow<ResultState<AuthResult>>
}