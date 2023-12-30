package com.template.sevenwindstests.model

import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<AuthResultState> {
        return repository.login(email, password)
    }
}