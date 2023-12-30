package com.template.sevenwindstests.data

import com.template.sevenwindstests.model.AuthData
import com.template.sevenwindstests.model.CoffeeItem
import com.template.sevenwindstests.model.LocationRespond
import com.template.sevenwindstests.model.LoginData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AppService {
    @POST("/auth/register")
    suspend fun register(
        @Body userData: LoginData
    ): AuthData

    @POST("/auth/login")
    suspend fun login(
        @Body userData: LoginData
    ): AuthData

    @GET("locations")
    suspend fun getLocations(
        @Header("Authorization") token: String
    ): List<LocationRespond>?

    @GET("location/{id}/menu")
    suspend fun getMenu(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : List<CoffeeItem>?
}
