package com.template.sevenwindstests.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.template.sevenwindstests.data.CoffeeRepositoryImpl
import com.template.sevenwindstests.model.CoffeeLocationResultState
import com.template.sevenwindstests.model.GetCoffeeItemsUseCase
import com.template.sevenwindstests.model.GetCoffeeLocationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CoffeeLocationsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = CoffeeRepositoryImpl(application)
    private val getCoffeeLocationsUseCase = GetCoffeeLocationsUseCase(repository)
    private val getCoffeeItemsUseCase = GetCoffeeItemsUseCase(repository)
    private val _state = MutableStateFlow<CoffeeLocationResultState>(CoffeeLocationResultState.Idle)
    val state = _state as StateFlow<CoffeeLocationResultState>
    init {
        getCoffeeLocations()
    }
    private fun getCoffeeLocations() {
        viewModelScope.launch {
            try {
                getCoffeeLocationsUseCase()
                    .catch {
                        _state.value = CoffeeLocationResultState.Error
                    }
                    .collect {
                        _state.value = it
                    }
            }
            catch (_: Exception) {
                _state.value = CoffeeLocationResultState.Error
            }
        }
    }
}
