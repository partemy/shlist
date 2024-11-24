package dev.partemy.shlist.common.shlist.api.api
import dev.partemy.shlist.common.shlist.api.util.apiCall
import dev.partemy.shlist.common.shlist.api.model.AuthResult
import dev.partemy.shlist.common.shlist.api.util.ApiRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class AuthService(
    private val httpClient: HttpClient,
) : IAuthService {
    override suspend fun createKey(): Result<String> =
        apiCall {
            httpClient.get(urlString = ApiRoutes.CREATE_KEY).body<String>()
        }


    override suspend fun authByKey(key: String): Result<AuthResult> =
            apiCall {
                httpClient.post(urlString = ApiRoutes.AUTH) {
                    parameter(key = "key", value = key)
                }.body<AuthResult>()
            }
}