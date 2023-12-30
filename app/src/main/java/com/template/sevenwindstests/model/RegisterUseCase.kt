package com.template.sevenwindstests.model

import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<AuthResultState> {
        return repository.register(email, password)
    }
}