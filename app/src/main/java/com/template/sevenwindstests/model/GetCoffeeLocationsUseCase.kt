package com.template.sevenwindstests.model

import kotlinx.coroutines.flow.Flow

class GetCoffeeLocationsUseCase(
    private val repository: CoffeeLocationsRepository
) {
    suspend operator fun invoke(): Flow<CoffeeLocationResultState> {
        return repository.getCoffeeLocations()
    }
}