package com.example.akochb1onboarding.webapi.entity

data class BlockWebEntity(
    val refBlockPrefix: Int? = null,
    val newProducers: Any? = null,
    val previous: String? = null,
    val scheduleVersion: Int? = null,
    val producerSignature: String? = null,
    val transactions: List<TransactionsItem?>? = null,
    val confirmed: Int? = null,
    val blockNum: Int? = null,
    val producer: String? = null,
    val transactionMroot: String? = null,
    val id: String? = null,
    val actionMroot: String? = null,
    val timestamp: String? = null
)

data class AuthorizationItem(
    val actor: String? = null,
    val permission: String? = null
)

data class TransactionsItem(
    val netUsageWords: Int? = null,
    val trx: Trx? = null,
    val cpuUsageUs: Int? = null,
    val status: String? = null
)

data class ActionsItem(
    val authorization: List<AuthorizationItem?>? = null,
    val hexData: String? = null,
    val data: Data? = null,
    val name: String? = null,
    val account: String? = null
)

data class Transaction(
    val refBlockPrefix: Long? = null,
    val maxCpuUsageMs: Int? = null,
    val contextFreeActions: List<Any?>? = null,
    val expiration: String? = null,
    val maxNetUsageWords: Int? = null,
    val delaySec: Int? = null,
    val refBlockNum: Int? = null,
    val actions: List<ActionsItem?>? = null
)

data class Trx(
    val packedTrx: String? = null,
    val packedContextFreeData: String? = null,
    val id: String? = null,
    val compression: String? = null,
    val signatures: List<String?>? = null,
    val transaction: Transaction? = null,
    val contextFreeData: List<Any?>? = null
)

data class Data(
    val address: String? = null,
    val size: Int? = null,
    val label: String? = null,
    val category: Int? = null,
    val hash: String? = null
)

