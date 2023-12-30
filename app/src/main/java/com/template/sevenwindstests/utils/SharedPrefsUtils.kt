package com.template.sevenwindstests.utils

import android.app.Application
import android.content.SharedPreferences

private const val TOKEN_EXTRA = "token"

class SharedPrefsUtils(application: Application) {
    private val sharedPreferences: SharedPreferences = application
            .getSharedPreferences(
                application.packageName,
                Application.MODE_PRIVATE
            )

    fun  setToken(token: String) {
        sharedPreferences.apply {
            edit().putString(TOKEN_EXTRA, "Bearer $token").apply()
        }
    }

    fun getToken(): String {
        return sharedPreferences.getString(TOKEN_EXTRA, "").orEmpty()
    }
}
