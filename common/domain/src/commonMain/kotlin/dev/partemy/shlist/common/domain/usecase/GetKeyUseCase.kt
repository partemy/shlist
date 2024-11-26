package dev.partemy.shlist.common.domain.usecase

import dev.partemy.shlist.common.domain.repository.IAuthRepository

class GetKeyUseCase(
    private val repository: IAuthRepository
) {
    operator fun invoke() = repository.getKey()
}