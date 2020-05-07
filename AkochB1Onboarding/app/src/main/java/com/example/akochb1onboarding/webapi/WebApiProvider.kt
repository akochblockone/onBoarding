package com.example.akochb1onboarding.webapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebApiProvider {
    private const val BASE_URL = "https://eos.greymass.com/v1/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val eosApi = retrofit.create(EosApi::class.java)
}
