package com.example.akochb1onboarding.webapi.mapper

import com.example.akochb1onboarding.BlockByIdQuery
import com.example.akochb1onboarding.LatestBlockQuery
import com.example.akochb1onboarding.domain.entity.Block
import com.google.gson.JsonObject
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse

class BlockInfoWebMapper {

    fun transform(block: JsonObject?): Block? {
        val input = block ?: return null
        return Block(
            id = input[ID_JSON_FIELD_NAME].asString ?: NOT_AVAILABLE,
            producer = input[PRODUCER_JSON_FIELD_NAME].asString ?: NOT_AVAILABLE,
            producerSignature = input[PRODUCER_SIGNATURE_JSON_FIELD_NAME].asString ?: NOT_AVAILABLE,
            previousBlock = input[PREVIOUS_BLOCK_JSON_FIELD_NAME].asString,
            transactionNumber = input[TRANSACTIONS_JSON_FIELD_NAME].asJsonArray.size(),
            fullBlock = input.toString(),
            creationTime = input[TIMESTAMP_JSON_FIELD_NAME].asString,
            blockNum = input[BLOCK_NUM_JSON_FIELD_NAME].asInt
        )
    }

    fun transform(input: BlockByIdQuery.BlockById): Block { // BlockById is Apollo generated
        return Block(
            id = input.id ?: NOT_AVAILABLE,
            producer = input.producer ?: NOT_AVAILABLE,
            producerSignature = input.producer_signature ?: NOT_AVAILABLE,
            previousBlock = input.previous,
            transactionNumber = input.transactions?.size ?: 0,
            fullBlock = input.full_block,
            creationTime = input.timestamp,
            blockNum = input.block_num ?: 0
        )
    }

    fun transform(input: LatestBlockQuery.LatestBlock): Block { // LatestBlock is Apollo generated
        return Block(
            id = input.id ?: NOT_AVAILABLE,
            producer = input.producer ?: NOT_AVAILABLE,
            producerSignature = input.producer_signature ?: NOT_AVAILABLE,
            previousBlock = input.previous,
            transactionNumber = input.transactions?.size ?: 0,
            fullBlock = input.full_block,
            creationTime = input.timestamp,
            blockNum = input.block_num ?: 0
        )
    }

    fun transform(block: GetBlockResponse?): Block? {
        val input = block ?: return null
        return Block(
            id = input.blockNum.toString(),
            producer = input.producer,
            producerSignature = input.producerSignature,
            previousBlock = input.previous,
            transactionNumber = input.transactions?.size ?: 0,
            fullBlock = input.toString(),
            creationTime = input.timestamp,
            blockNum = input.blockNum.toInt()
        )
    }

    companion object {
        const val NOT_AVAILABLE = "N/A"
        const val ID_JSON_FIELD_NAME = "id"
        const val BLOCK_NUM_JSON_FIELD_NAME = "block_num"
        const val PRODUCER_JSON_FIELD_NAME = "producer"
        const val PRODUCER_SIGNATURE_JSON_FIELD_NAME = "producer_signature"
        const val PREVIOUS_BLOCK_JSON_FIELD_NAME = "previous"
        const val TRANSACTIONS_JSON_FIELD_NAME = "transactions"
        const val TIMESTAMP_JSON_FIELD_NAME = "timestamp"
    }
}