package dev.partemy.shlist.common.data.remote.api.impl

import dev.partemy.shlist.common.data.ResultState
import dev.partemy.shlist.common.data.remote.ApiRoutes
import dev.partemy.shlist.common.data.remote.api.LoginService
import dev.partemy.shlist.common.data.remote.apiCall
import dev.partemy.shlist.common.data.remote.model.AuthResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LoginServiceImpl(
    private val httpClient: HttpClient,
) : LoginService {
    override suspend fun createKey(): Flow<ResultState<String>> =
        flowOf(
            apiCall {
                httpClient.get(urlString = ApiRoutes.CREATE_KEY).body<String>()
            }
        )

    override suspend fun authByKey(key: String): Flow<ResultState<AuthResult>> =
        flowOf(
            apiCall {
                httpClient.post(urlString = ApiRoutes.AUTH) {
                    parameter(key = "key", value = key)
                }.body<AuthResult>()
            }
        )

}