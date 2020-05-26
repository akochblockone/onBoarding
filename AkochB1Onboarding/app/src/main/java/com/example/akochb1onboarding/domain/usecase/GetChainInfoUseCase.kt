package com.example.akochb1onboarding.domain.usecase

import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.repository.ChainRepository

interface GetChainInfoUseCase {
    suspend fun getLastChainInfo(): ChainInfo?
}

class GetChainInfoUseCaseImpl(private val chainRepository: ChainRepository) : GetChainInfoUseCase {
    override suspend fun getLastChainInfo(): ChainInfo? {
        return chainRepository.getHeadChainInfo()
    }
}