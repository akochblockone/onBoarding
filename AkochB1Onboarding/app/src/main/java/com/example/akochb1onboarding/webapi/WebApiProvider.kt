package com.example.akochb1onboarding.webapi

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val NET_TIMEOUT_SECONDS = 10L
const val GRAPHQL_URL = "http://192.168.1.6:4000/graphql"

object WebApiProvider {
    private const val BASE_URL = "https://eos.greymass.com/v1/"
    private val httpClient = OkHttpClient().newBuilder()
        .connectTimeout(NET_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NET_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(NET_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val eosApi = retrofit.create(EosApi::class.java)

    val apolloClient = ApolloClient.builder()
        .okHttpClient(httpClient)
        .serverUrl(GRAPHQL_URL)
        .build()
}
