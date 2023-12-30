package com.template.sevenwindstests.presentation

import androidx.lifecycle.ViewModel
import com.template.sevenwindstests.model.CoffeeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoffeeOrderViewModel(orders: List<CoffeeItem>): ViewModel() {
    private val _orders = MutableStateFlow(orders)
    val orders = _orders as StateFlow<List<CoffeeItem>>
    fun addCoffeeItem(coffeeItem: CoffeeItem) {
        val oldItems = orders.value.toMutableList()
        oldItems.replaceAll { if (it.id == coffeeItem.id) it.copy(count = (it.count ?: 0) + 1) else it }
        _orders.value = oldItems
    }
    fun removeCoffeeItem(coffeeItem: CoffeeItem) {
        if (coffeeItem.count == 0) return
        val oldItems = orders.value.toMutableList()
        oldItems.replaceAll { if (it.id == coffeeItem.id) it.copy(count = (it.count ?: 0) - 1) else it }
        if (oldItems.any { it.count == 0 }) {
            oldItems.removeAll { it.count == 0 }
        }
        _orders.value = oldItems
    }
}