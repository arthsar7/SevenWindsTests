package com.template.sevenwindstests.data

import android.app.Application
import android.util.Log
import com.template.sevenwindstests.model.CoffeeItem
import com.template.sevenwindstests.model.CoffeeItemResultState
import com.template.sevenwindstests.model.CoffeeLocationsRepository
import com.template.sevenwindstests.model.CoffeeLocationResultState
import com.template.sevenwindstests.utils.SharedPrefsUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoffeeRepositoryImpl(application: Application): CoffeeLocationsRepository {
    private val apiService = ApiFactory.apiService
    private val sharedPrefsUtils = SharedPrefsUtils(application)
    override suspend fun getCoffeeLocations(): Flow<CoffeeLocationResultState> {
        return flow {
            try {
                val locations = apiService.getLocations(sharedPrefsUtils.getToken())
                if (locations.isNullOrEmpty()) {
                    emit(CoffeeLocationResultState.Error)
                }
                else {
                    emit(CoffeeLocationResultState.Success(locations))
                }
            }
            catch (_: Exception) {
                emit(CoffeeLocationResultState.Error)
            }
        }
    }

    override suspend fun getCoffeeItems(id: Int): Flow<CoffeeItemResultState> {
        return flow {
            try {
                val items = apiService.getMenu(sharedPrefsUtils.getToken(), id)
                if (items.isNullOrEmpty()) {
                    emit(CoffeeItemResultState.Error)
                }
                else {
                    emit(CoffeeItemResultState.Success(items))
                    Log.d("TAG", items.toString())
                }
            }
            catch (_: Exception) {
                emit(CoffeeItemResultState.Error)
            }
        }
    }
}