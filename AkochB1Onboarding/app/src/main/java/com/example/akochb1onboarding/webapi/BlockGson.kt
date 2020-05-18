package com.example.akochb1onboarding.webapi

import com.google.gson.annotations.SerializedName

data class BlockGson(

	@field:SerializedName("ref_block_prefix")
	val refBlockPrefix: Int? = null,

	@field:SerializedName("new_producers")
	val newProducers: NewProducers? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("schedule_version")
	val scheduleVersion: Int? = null,

	@field:SerializedName("producer_signature")
	val producerSignature: String? = null,

	@field:SerializedName("transactions")
	val transactions: List<TransactionsItem?>? = null,

	@field:SerializedName("confirmed")
	val confirmed: Int? = null,

	@field:SerializedName("block_num")
	val blockNum: Int? = null,

	@field:SerializedName("producer")
	val producer: String? = null,

	@field:SerializedName("transaction_mroot")
	val transactionMroot: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("action_mroot")
	val actionMroot: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

data class ActionsItem(

	@field:SerializedName("authorization")
	val authorization: List<AuthorizationItem?>? = null,

	@field:SerializedName("hex_data")
	val hexData: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("account")
	val account: String? = null
)

data class NewProducers(

	@field:SerializedName("version")
	val version: Int? = null,

	@field:SerializedName("producers")
	val producers: List<ProducersItem?>? = null
)

data class Trx(

	@field:SerializedName("packed_trx")
	val packedTrx: String? = null,

	@field:SerializedName("packed_context_free_data")
	val packedContextFreeData: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("compression")
	val compression: String? = null,

	@field:SerializedName("signatures")
	val signatures: List<String?>? = null,

	@field:SerializedName("transaction")
	val transaction: Transaction? = null,

	@field:SerializedName("context_free_data")
	val contextFreeData: List<String?>? = null
)

data class TransactionsItem(

	@field:SerializedName("net_usage_words")
	val netUsageWords: Int? = null,

	@field:SerializedName("trx")
	val trx: Trx? = null,

	@field:SerializedName("cpu_usage_us")
	val cpuUsageUs: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AuthorizationItem(

	@field:SerializedName("actor")
	val actor: String? = null,

	@field:SerializedName("permission")
	val permission: String? = null
)

data class ProducersItem(

	@field:SerializedName("producer_name")
	val producerName: String? = null,

	@field:SerializedName("block_signing_key")
	val blockSigningKey: String? = null
)

data class Transaction(

	@field:SerializedName("ref_block_prefix")
	val refBlockPrefix: Long? = null,

	@field:SerializedName("max_cpu_usage_ms")
	val maxCpuUsageMs: Int? = null,

	@field:SerializedName("context_free_actions")
	val contextFreeActions: List<ActionsItem?>? = null,

	@field:SerializedName("expiration")
	val expiration: String? = null,

	@field:SerializedName("max_net_usage_words")
	val maxNetUsageWords: Int? = null,

	@field:SerializedName("delay_sec")
	val delaySec: Int? = null,

	@field:SerializedName("ref_block_num")
	val refBlockNum: Int? = null,

	@field:SerializedName("actions")
	val actions: List<ActionsItem?>? = null
)

data class Data(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("label_")
	val label: String? = null,

	@field:SerializedName("category")
	val category: Int? = null,

	@field:SerializedName("hash")
	val hash: String? = null
)
