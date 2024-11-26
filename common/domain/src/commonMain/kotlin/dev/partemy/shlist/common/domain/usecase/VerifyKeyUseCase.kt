package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IAuthRepository

class VerifyKeyUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(key: String) = repository.passKey(key)
}