package dev.partemy.shlist.common.data.remote

import dev.partemy.shlist.common.shlist.api.api.IAuthService

class AuthRemoteDataSource(
    private val authService: IAuthService,
) : IAuthRemoteDataSource {
    override suspend fun createKey(): Result<String> {
        val request = authService.createKey()
        return if (request.isFailure) Result.failure(
            exception = request.exceptionOrNull() ?: Exception()
        ) else Result.success(request.getOrThrow())
    }

    override suspend fun passKey(key: String): Result<Nothing?> {
        val request = authService.authByKey(key)
        return if (request.getOrNull()?.success == false || request.isFailure) Result.failure(
            request.exceptionOrNull() ?: Exception()
        ) else Result.success(null)
    }
}