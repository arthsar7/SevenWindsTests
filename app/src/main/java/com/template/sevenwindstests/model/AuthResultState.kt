package com.template.sevenwindstests.model

sealed class AuthResultState {
    object Idle : AuthResultState()
    class Success(
        val response: AuthData
    ) : AuthResultState()
    object Error : AuthResultState()
}
