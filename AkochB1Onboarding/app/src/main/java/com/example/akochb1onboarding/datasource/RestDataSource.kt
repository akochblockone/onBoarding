package com.example.akochb1onboarding.datasource

import android.util.Log
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.BlockResponse
import com.example.akochb1onboarding.domain.entity.ErrorBlockResponse
import com.example.akochb1onboarding.domain.entity.SuccessBlockResponse
import com.example.akochb1onboarding.repository.BlockRepositoryImpl
import com.example.akochb1onboarding.webapi.WebApiProvider
import com.example.akochb1onboarding.webapi.mapper.BlockInfoWebMapper
import com.google.gson.JsonObject

class RestDataSource(val mapper: BlockInfoWebMapper) : WebDataSource {

    override suspend fun getLatestBlock(): BlockResponse {
        return ErrorBlockResponse("")
    }

    override suspend fun getBlock(blockId: String): BlockResponse {
        try {
            val eosApi = WebApiProvider.eosApi
            val requestBody = JsonObject()
            requestBody.addProperty(BlockRepositoryImpl.BLOCK_ID_REQUEST_BODY_KEY, blockId)
            val response = eosApi.getBlockInfo(requestBody)
            return if (response.isSuccessful) {
                val block = mapper.transform(response.body())
                if (block == null) {
                    ErrorBlockResponse("null block")
                } else {
                    SuccessBlockResponse(block)
                }
            } else {
                Log.e("RestDataSource", response.errorBody()?.toString() ?: "undefined")
                ErrorBlockResponse("Response error: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("blockRepo", "e", e)
            return ErrorBlockResponse("Network error: ${e.message}")
        }
    }
}