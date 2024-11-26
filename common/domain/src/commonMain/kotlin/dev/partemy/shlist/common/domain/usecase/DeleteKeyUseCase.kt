package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IAuthRepository

class DeleteKeyUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke() = repository.deleteKey()
}