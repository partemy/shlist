package dev.partemy.shlist.common.data.remote

interface IAuthRemoteDataSource {
    suspend fun createKey() : Result<String>
    suspend fun passKey(key: String) : Result<Nothing?>
}