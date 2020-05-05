package com.example.akochb1onboarding.repository

import android.util.Log
import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.repository.ChainRepository
import com.example.akochb1onboarding.webapi.WebApiProvider
import com.example.akochb1onboarding.webapi.mapper.ChainInfoWebMapper

class ChainRepositoryImpl: ChainRepository {
    override fun getHeadChainInfo(): ChainInfo? {
        val response = WebApiProvider.INSTANCE.getEosApi().getChainInfo().execute()
        if (response.isSuccessful) {
            return ChainInfoWebMapper().transform(response.body())
        } else {
            Log.e("chainRepo", response.errorBody()?.string() ?: "undefined")
        }
        return null
    }
}