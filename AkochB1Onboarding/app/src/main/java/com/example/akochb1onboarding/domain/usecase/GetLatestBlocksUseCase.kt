package com.example.akochb1onboarding.domain.usecase

import androidx.paging.DataSource
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.BlockResponse
import com.example.akochb1onboarding.domain.repository.BlockRepository

interface GetLatestBlocksUseCase {
    suspend fun getBlock(id: String): BlockResponse
    suspend fun getLatestBlock(): BlockResponse
    suspend fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity>
}

class GetLatestBlocksUseCaseImpl(private val repository: BlockRepository) : GetLatestBlocksUseCase {
    override suspend fun getBlock(id: String): BlockResponse {
        return repository.getBlock(id)
    }

    override suspend fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity> {
        return repository.getBlocksPaginated()
    }

    override suspend fun getLatestBlock(): BlockResponse {
        return repository.getLatestBlock()
    }
}