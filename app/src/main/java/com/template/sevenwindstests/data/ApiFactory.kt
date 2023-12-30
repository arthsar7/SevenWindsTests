package com.template.sevenwindstests.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {
    private const val BASE_URL = "http://147.78.66.203:3210/"
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )).build()
    val apiService: AppService = retrofit.create(AppService::class.java)
}