package dev.partemy.shlist.common.shlist.api.api

import dev.partemy.shlist.common.shlist.api.model.AuthResult

interface IAuthService {
    suspend fun createKey(): Result<String>
    suspend fun authByKey(key: String): Result<AuthResult>
}