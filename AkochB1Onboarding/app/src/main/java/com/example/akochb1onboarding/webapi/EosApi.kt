package com.example.akochb1onboarding.webapi

import com.example.akochb1onboarding.webapi.entity.ChainInfoWebEntity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface EosApi {

    @GET("chain/get_info")
    suspend fun getChainInfo(): Response<ChainInfoWebEntity>

    @Headers("Content-Type: application/json")
    @POST("chain/get_block")
    suspend fun getBlockInfo(@Body body: JsonObject): Response<JsonObject>
}
