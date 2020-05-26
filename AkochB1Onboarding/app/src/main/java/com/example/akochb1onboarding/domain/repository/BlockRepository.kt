package com.example.akochb1onboarding.domain.repository

import androidx.paging.DataSource
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.BlockResponse

interface BlockRepository {
    suspend fun getBlock(blockId: String): BlockResponse
    suspend fun getLatestBlock(): BlockResponse
    suspend fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity>
}