package com.template.sevenwindstests.model

import kotlinx.coroutines.flow.Flow

interface CoffeeLocationsRepository {
    suspend fun getCoffeeLocations(): Flow<CoffeeLocationResultState>
    suspend fun getCoffeeItems(id: Int): Flow<CoffeeItemResultState>
}