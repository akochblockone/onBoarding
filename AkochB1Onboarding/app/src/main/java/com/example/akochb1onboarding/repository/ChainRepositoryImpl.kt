package com.example.akochb1onboarding.repository

import android.util.Log
import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.repository.ChainRepository
import com.example.akochb1onboarding.webapi.EosApi
import com.example.akochb1onboarding.webapi.mapper.ChainInfoWebMapper
import java.lang.Exception

class ChainRepositoryImpl(
    private val eosApi: EosApi,
    private val chainInfoWebMapper: ChainInfoWebMapper
) : ChainRepository {
    override fun getHeadChainInfo(): ChainInfo? {
        try {
            val response = eosApi.getChainInfo().execute()
            if (response.isSuccessful) {
                return chainInfoWebMapper.transform(response.body())
            } else {
                Log.e("chainRepo", response.errorBody()?.string() ?: "undefined")
            }
            return null
        } catch (e: Exception) {
            Log.e("chainRepo", "Error getting the chain info", e)
        }
        return null
    }
}