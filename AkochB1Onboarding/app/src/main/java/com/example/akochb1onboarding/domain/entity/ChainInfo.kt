package com.example.akochb1onboarding.domain.entity

data class ChainInfo(
    val serverVersion: String? = null,
    val chainId: String? = null,
    val headBlockNum: Long? = null,
    val lastIrreversibleBlockNum: Long? = null,
    val lastIrreversibleBlockId: String? = null,
    val headBlockId: String? = null,
    val headBlockTime: String? = null, //"2020-04-24T13:20:45.000"
    val headBlockProducer: String? = null, //"eosnationftw"
    val virtualBlockCpuLimit: Long? = null,
    val virtualBlockNetLimit: Long? = null,
    val blockCpuLimit: Long? = null,
    val blockNetLimit: Long? = null,
    val serverVersionString: String? = null,
    val forkDbHeadBlockNum: Long? = null,
    val forkDbHeadBlockId: String? = null,
    val serveFullVersionString: String? = null
)