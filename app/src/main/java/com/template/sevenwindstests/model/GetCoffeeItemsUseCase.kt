package com.template.sevenwindstests.model

class GetCoffeeItemsUseCase(private val repository: CoffeeLocationsRepository) {
    suspend operator fun invoke(id: Int) = repository.getCoffeeItems(id)
}
