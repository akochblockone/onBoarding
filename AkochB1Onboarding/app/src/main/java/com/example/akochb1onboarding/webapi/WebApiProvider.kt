package com.example.akochb1onboarding.webapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebApiProvider private constructor() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val eosApi = retrofit.create(EosApi::class.java)

    fun getEosApi(): EosApi = eosApi

    companion object {
        const val BASE_URL = "https://eos.greymass.com/v1/"
        val INSTANCE = WebApiProvider()
    }
}
