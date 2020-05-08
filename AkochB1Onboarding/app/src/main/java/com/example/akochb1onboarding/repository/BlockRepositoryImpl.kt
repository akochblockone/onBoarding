package com.example.akochb1onboarding.repository

import android.util.Log
import androidx.paging.DataSource
import com.example.akochb1onboarding.db.dao.BlockDao
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.domain.repository.BlockRepository
import com.example.akochb1onboarding.webapi.EosApi
import com.example.akochb1onboarding.webapi.mapper.BlockInfoWebMapper
import com.google.gson.JsonObject
import java.lang.Exception

class BlockRepositoryImpl(
    private val eosApi: EosApi,
    private val blockDao: BlockDao,
    private val blockInfoWebMapper: BlockInfoWebMapper
) : BlockRepository {

    override fun getBlock(blockId: String): Block? {
        val blockEntity = blockDao.getBlock(blockId)
        if (blockEntity != null) {
            return blockEntity.toBlock().apply { cached = true }
        }
        try {
            val requestBody = JsonObject()
            requestBody.addProperty(BLOCK_ID_REQUEST_BODY_KEY, blockId)
            val response = eosApi
                .getBlockInfo(requestBody)
                .execute()
            if (response.isSuccessful) {
                val block = blockInfoWebMapper.transform(response.body())
                block?.let {
                    blockDao.insert(BlockEntity(it))
                }
                return block
            } else {
                Log.e("blockRepo", response.errorBody()?.string() ?: "undefined")
            }
            return null
        } catch (e: Exception) {
            Log.e("blockRepo", "e", e)
            return null
        }
    }

    override fun getBlocksPaginated(): DataSource.Factory<Int, BlockEntity> {
        return blockDao.getPaged()
    }

    companion object {
        const val BLOCK_ID_REQUEST_BODY_KEY = "block_num_or_id"
    }
}