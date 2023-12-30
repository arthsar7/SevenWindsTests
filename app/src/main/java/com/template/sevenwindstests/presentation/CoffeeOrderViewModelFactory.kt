package com.template.sevenwindstests.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.template.sevenwindstests.model.CoffeeItem

class CoffeeOrderViewModelFactory(
    private val orders: List<CoffeeItem>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeOrderViewModel::class.java)) {
            return CoffeeOrderViewModel(orders) as T
        }
        return super.create(modelClass)
    }
}