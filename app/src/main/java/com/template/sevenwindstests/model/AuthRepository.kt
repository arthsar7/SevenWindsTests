package com.template.sevenwindstests.model

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(email: String, password: String): Flow<AuthResultState>

    suspend fun login(email: String, password: String): Flow<AuthResultState>

}