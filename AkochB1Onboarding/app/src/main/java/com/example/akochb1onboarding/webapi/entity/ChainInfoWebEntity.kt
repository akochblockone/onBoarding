package com.example.akochb1onboarding.webapi.entity

import com.google.gson.annotations.SerializedName

open class ChainInfoWebEntity(
    @SerializedName("server_version") var serverVersion: String? = null,
    @SerializedName("chain_id") var chainId: String? = null,
    @SerializedName("head_block_num") var headBlockNum: Long? = null,
    @SerializedName("last_irreversible_block_num") var lastIrreversibleBlockNum: Long? = null,
    @SerializedName("last_irreversible_block_id") var lastIrreversibleBlockId: String? = null,
    @SerializedName("head_block_id") var headBlockId: String? = null,
    @SerializedName("head_block_time") var headBlockTime: String? = null,//"2020-04-24T13:20:45.000"
    @SerializedName("head_block_producer") var headBlockProducer: String? = null, //"eosnationftw"
    @SerializedName("virtual_block_cpu_limit") var virtualBlockCpuLimit: Long? = null,
    @SerializedName("virtual_block_net_limit") var virtualBlockNetLimit: Long? = null,
    @SerializedName("block_cpu_limit") var blockCpuLimit: Long? = null,
    @SerializedName("block_net_limit") var blockNetLimit: Long? = null,
    @SerializedName("server_version_string") var serverVersionString: String? = null,
    @SerializedName("fork_db_head_block_num") var forkDbHeadBlockNum: Long? = null,
    @SerializedName("fork_db_head_block_id") var forkDbHeadBlockId: String? = null,
    @SerializedName("server_full_version_string") var serveFullVersionString: String? = null
)
