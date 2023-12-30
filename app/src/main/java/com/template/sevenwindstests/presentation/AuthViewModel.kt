package com.template.sevenwindstests.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.template.sevenwindstests.data.AuthRepositoryImpl
import com.template.sevenwindstests.model.LoginUseCase
import com.template.sevenwindstests.model.RegisterUseCase
import com.template.sevenwindstests.model.AuthResultState
import com.template.sevenwindstests.utils.SharedPrefsUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepositoryImpl()
    private val registerUseCase = RegisterUseCase(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val sharedPrefsUtils = SharedPrefsUtils(application)
    private val _state = MutableStateFlow<AuthResultState>(AuthResultState.Idle)
    val state = _state as StateFlow<AuthResultState>

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                registerUseCase(email, password)
                    .catch {
                        _state.value = AuthResultState.Error
                    }
                    .collect {
                        _state.value = it
                    }
            }
            catch (_: Exception) {
                _state.value = AuthResultState.Error
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                loginUseCase(email, password)
                    .catch {
                        _state.value = AuthResultState.Error
                    }
                    .collect {
                        _state.value = it
                        if (it is AuthResultState.Success) {
                            sharedPrefsUtils.setToken(it.response.token!!)
                        }
                    }
            }
            catch (_: Exception) {
                _state.value = AuthResultState.Error
            }
        }
    }
}