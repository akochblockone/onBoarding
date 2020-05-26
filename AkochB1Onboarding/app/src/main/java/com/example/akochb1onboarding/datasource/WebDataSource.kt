package com.example.akochb1onboarding.datasource

import com.example.akochb1onboarding.domain.entity.BlockResponse

interface WebDataSource {
    suspend fun getLatestBlock(): BlockResponse
    suspend fun getBlock(blockId: String): BlockResponse
}