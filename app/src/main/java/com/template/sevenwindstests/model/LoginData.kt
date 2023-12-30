package com.template.sevenwindstests.model

import retrofit2.http.Query

data class LoginData(
    @Query("login") val login: String,
    @Query("password") val password: String
)
