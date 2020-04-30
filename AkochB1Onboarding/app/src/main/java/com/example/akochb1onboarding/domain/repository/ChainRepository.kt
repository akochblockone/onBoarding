package com.example.akochb1onboarding.domain.repository

import com.example.akochb1onboarding.domain.entity.ChainInfo

interface ChainRepository {
    fun getHeadChainInfo(): ChainInfo?
}