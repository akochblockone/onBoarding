package com.example.akochb1onboarding.datasource

import android.util.Log
import com.apollographql.apollo.coroutines.toDeferred
import com.example.akochb1onboarding.BlockByIdQuery
import com.example.akochb1onboarding.LatestBlockQuery
import com.example.akochb1onboarding.domain.entity.BlockResponse
import com.example.akochb1onboarding.domain.entity.ErrorBlockResponse
import com.example.akochb1onboarding.domain.entity.SuccessBlockResponse
import com.example.akochb1onboarding.webapi.WebApiProvider
import com.example.akochb1onboarding.webapi.mapper.BlockInfoWebMapper

class GraphQlDataSource(val mapper: BlockInfoWebMapper) : WebDataSource {

    override suspend fun getLatestBlock(): BlockResponse {
        try {
            val apolloClient = WebApiProvider.apolloClient
            val latestBlockQuery = LatestBlockQuery()
            val response = apolloClient.query(latestBlockQuery).toDeferred().await()
            if (response.hasErrors()) {
                return ErrorBlockResponse(response.errors?.firstOrNull()?.message ?: "N/A")
            } else {
                val data = response.data?.latestBlock
                        ?: return ErrorBlockResponse("No data in response")

                return SuccessBlockResponse(mapper.transform(data))
            }
        } catch (e: Exception) {
            Log.e("graphql data source", "getLatestBlock", e)
            return ErrorBlockResponse(e.message.toString())
        }
    }

    override suspend fun getBlock(blockId: String): BlockResponse {
        try {
            val apolloClient = WebApiProvider.apolloClient
            val blockByIdQuery = BlockByIdQuery(blockId)
            val response = apolloClient.query(blockByIdQuery).toDeferred().await()
            if (response.hasErrors()) {
                return ErrorBlockResponse(response.errors?.firstOrNull()?.message ?: "N/A")
            } else {
                val data = response.data?.blockById
                    ?: return ErrorBlockResponse("No data in response")

                return SuccessBlockResponse(mapper.transform(data))
            }
        } catch (e: Exception) {
            Log.e("graphql data source", "getBlock", e)
            return ErrorBlockResponse(e.message.toString())
        }
    }
}