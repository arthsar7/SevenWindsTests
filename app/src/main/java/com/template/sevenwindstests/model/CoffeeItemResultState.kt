package com.template.sevenwindstests.model

sealed class CoffeeItemResultState {
    object Idle: CoffeeItemResultState()
    object Error: CoffeeItemResultState()
    data class Success(val data: List<CoffeeItem>): CoffeeItemResultState()
}
