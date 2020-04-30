package com.example.akochb1onboarding.domain.usecase

import androidx.paging.DataSource
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.domain.repository.BlockRepository

interface GetLatestBlocksUseCase {
    fun getBlock(id: String): Block?
    fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity>
}

class GetLatestBlocksUseCaseImpl(private val repository: BlockRepository) : GetLatestBlocksUseCase {
    override fun getBlock(id: String): Block? {
        return repository.getBlock(id)
    }

    override fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity> {
        return repository.getBlocksPaginated()
    }

}