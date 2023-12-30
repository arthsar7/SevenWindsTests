package com.template.sevenwindstests.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.template.sevenwindstests.data.CoffeeRepositoryImpl
import com.template.sevenwindstests.model.CoffeeItem
import com.template.sevenwindstests.model.CoffeeItemResultState
import com.template.sevenwindstests.model.GetCoffeeItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CoffeeItemsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = CoffeeRepositoryImpl(application)
    private val getCoffeeItemsUseCase = GetCoffeeItemsUseCase(repository)
    private val _state = MutableStateFlow<CoffeeItemResultState>(CoffeeItemResultState.Idle)
    val state = _state as StateFlow<CoffeeItemResultState>
    fun getCoffeeItem(id: Int) {
        viewModelScope.launch {
            try {
                getCoffeeItemsUseCase(id)
                    .catch {
                        _state.value = CoffeeItemResultState.Error
                    }
                    .collect {
                        _state.value = it
                    }
            }
            catch (_: Exception) {
                _state.value = CoffeeItemResultState.Error
            }
        }
    }
    fun onPlusItem(coffeeItem: CoffeeItem) {
        val oldItems = (_state.value as CoffeeItemResultState.Success).data.toMutableList()
        oldItems.replaceAll { if (it.id == coffeeItem.id) it.copy(count = (it.count ?: 0) + 1) else it }
        _state.value = CoffeeItemResultState.Success(oldItems)
    }
    fun onMinusItem(coffeeItem: CoffeeItem) {
        if (coffeeItem.count == 0) return
        val oldItems = (_state.value as CoffeeItemResultState.Success).data.toMutableList()
        oldItems.replaceAll { if (it.id == coffeeItem.id) it.copy(count = (it.count ?: 0) - 1) else it }
        _state.value = CoffeeItemResultState.Success(oldItems)
    }

    fun orderList(): List<CoffeeItem> {
        return (_state.value as CoffeeItemResultState.Success).data
            .filter { it.count != null && it.count!! > 0 }
    }
}