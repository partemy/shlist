package dev.partemy.shlist.common.data.remote.impl

import dev.partemy.shlist.common.data.remote.BaseRemoteDataSource
import dev.partemy.shlist.common.data.remote.IAuthRemoteDataSource
import dev.partemy.shlist.common.shlist.api.api.IAuthService

class AuthRemoteDataSource(
    private val authService: IAuthService,
) : IAuthRemoteDataSource, BaseRemoteDataSource() {
    override suspend fun createKey(): Result<String> {
        val request = authService.createKey()
        return handleRequest(request) { it }
    }

    override suspend fun passKey(key: String): Result<Nothing?> {
        val request = authService.authByKey(key)
        return handleRequest(request) { null }
    }
}