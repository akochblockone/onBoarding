package com.example.akochb1onboarding.domain.usecase

import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.repository.ChainRepository

interface GetChainInfoUseCase {
    fun getLastChainInfo(): ChainInfo?
}

class GetChainInfoUseCaseImpl(private val chainRepository: ChainRepository) : GetChainInfoUseCase {
    override fun getLastChainInfo(): ChainInfo? {
        return chainRepository.getHeadChainInfo()
    }
}