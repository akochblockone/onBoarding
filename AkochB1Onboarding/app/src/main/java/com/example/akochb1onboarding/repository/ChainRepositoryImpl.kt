package com.example.akochb1onboarding.repository

import android.util.Log
import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.repository.ChainRepository
import com.example.akochb1onboarding.webapi.EosApi
import com.example.akochb1onboarding.webapi.mapper.ChainInfoWebMapper

class ChainRepositoryImpl(
    private val eosApi: EosApi,
    private val chainInfoWebMapper: ChainInfoWebMapper
) : ChainRepository {
    override fun getHeadChainInfo(): ChainInfo? {
        val response = eosApi.getChainInfo().execute()
        if (response.isSuccessful) {
            return chainInfoWebMapper.transform(response.body())
        } else {
            Log.e("chainRepo", response.errorBody()?.string() ?: "undefined")
        }
        return null
    }
}