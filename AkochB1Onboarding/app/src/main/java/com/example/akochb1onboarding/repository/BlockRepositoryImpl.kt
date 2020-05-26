package com.example.akochb1onboarding.repository

import androidx.paging.DataSource
import com.example.akochb1onboarding.datasource.WebDataSource
import com.example.akochb1onboarding.db.dao.BlockDao
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.BlockResponse
import com.example.akochb1onboarding.domain.entity.SuccessBlockResponse
import com.example.akochb1onboarding.domain.repository.BlockRepository
import kotlin.coroutines.coroutineContext

class BlockRepositoryImpl(
    private val webDataSource: WebDataSource,
    private val blockDao: BlockDao
) : BlockRepository {

    override suspend fun getBlock(blockId: String): BlockResponse {
        val blockEntity = blockDao.getBlock(blockId)
        if (blockEntity != null) {
            return SuccessBlockResponse(blockEntity.toBlock().apply { cached = true })
        }
        val response = webDataSource.getBlock(blockId)
        response.block?.let { // block data is not null only when the request is successful
            blockDao.insert(BlockEntity(it))
        }
        return webDataSource.getBlock(blockId)
    }

    override suspend fun getLatestBlock(): BlockResponse {
        val response = webDataSource.getLatestBlock()
        response.block?.let { // block data is not null only when the request is successful
            blockDao.insert(BlockEntity(it))
        }
        return response
    }

    override suspend fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity> {
        return blockDao.getPaged()
    }

    companion object {
        const val BLOCK_ID_REQUEST_BODY_KEY = "block_num_or_id"
    }
}