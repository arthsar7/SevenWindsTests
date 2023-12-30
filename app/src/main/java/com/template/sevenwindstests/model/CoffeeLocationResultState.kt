package com.template.sevenwindstests.model

sealed class CoffeeLocationResultState {
    object Idle : CoffeeLocationResultState()
    object Error : CoffeeLocationResultState()
    data class Success(val response: List<LocationRespond>) : CoffeeLocationResultState()
}
