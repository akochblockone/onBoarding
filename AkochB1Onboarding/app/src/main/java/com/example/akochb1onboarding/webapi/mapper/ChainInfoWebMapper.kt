package com.example.akochb1onboarding.webapi.mapper

import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.webapi.entity.ChainInfoWebEntity

class ChainInfoWebMapper {

    fun transform(webEntity: ChainInfoWebEntity?): ChainInfo? {
        val input = webEntity ?: return null
        return ChainInfo(
            serverVersion = input.serverVersion,
            chainId = input.chainId,
            headBlockNum = input.headBlockNum,
            lastIrreversibleBlockNum = input.lastIrreversibleBlockNum,
            lastIrreversibleBlockId = input.lastIrreversibleBlockId,
            headBlockId = input.headBlockId,
            headBlockTime = input.headBlockTime,
            headBlockProducer = input.headBlockProducer,
            virtualBlockCpuLimit = input.virtualBlockCpuLimit,
            virtualBlockNetLimit = input.virtualBlockNetLimit,
            blockCpuLimit = input.blockCpuLimit,
            blockNetLimit = input.blockNetLimit,
            serverVersionString = input.serverVersionString,
            forkDbHeadBlockNum = input.forkDbHeadBlockNum,
            forkDbHeadBlockId = input.forkDbHeadBlockId,
            serveFullVersionString = input.serveFullVersionString
        )
    }
}