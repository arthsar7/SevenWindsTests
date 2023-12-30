package com.template.sevenwindstests.data

import com.template.sevenwindstests.model.AuthRepository
import com.template.sevenwindstests.model.LoginData
import com.template.sevenwindstests.model.AuthResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl: AuthRepository {
    private val apiService = ApiFactory.apiService
    override suspend fun register(email: String, password: String): Flow<AuthResultState> {
        return flow {
            val registerData = LoginData(email, password)
            val authData = apiService.register(registerData)
            if (authData.token != null) {
                emit(AuthResultState.Success(authData))
            }
            else {
                emit(AuthResultState.Error)
            }
        }
    }
    override suspend fun login(email: String, password: String): Flow<AuthResultState> {
        return flow {
            val loginData = LoginData(email, password)
            val authData = apiService.login(loginData)
            if (authData.token != null) {
                emit(AuthResultState.Success(authData))
            }
            else {
                emit(AuthResultState.Error)
            }
        }
    }

}