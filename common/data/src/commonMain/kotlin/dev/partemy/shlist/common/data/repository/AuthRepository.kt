package dev.partemy.shlist.common.data.repository

import dev.partemy.shlist.common.data.local.IAuthLocalDataSource
import dev.partemy.shlist.common.data.remote.IAuthRemoteDataSource
import dev.partemy.shlist.common.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val authRemoteDataSource: IAuthRemoteDataSource,
    private val authLocalDataSource: IAuthLocalDataSource,
) : IAuthRepository {
    override suspend fun createKey(): Result<Nothing?> {
        val apiRequest = authRemoteDataSource.createKey()
        return if (apiRequest.isSuccess) {
            authLocalDataSource.setKey(apiRequest.getOrThrow())
            Result.success(null)
        } else Result.failure(exception = apiRequest.exceptionOrNull() ?: Exception())
    }

    override suspend fun passKey(key: String): Result<Nothing?> {
        val apiRequest = authRemoteDataSource.passKey(key)
        return if (apiRequest.isSuccess) {
            authLocalDataSource.setKey(key)
            Result.success(null)
        } else Result.failure(exception = apiRequest.exceptionOrNull() ?: Exception())
    }

    override suspend fun deleteKey(): Result<Nothing?> {
        return try {
            authLocalDataSource.setKey("")
            authLocalDataSource.clearAll()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override fun getKey(): Flow<String?> =  authLocalDataSource.getKey()

}