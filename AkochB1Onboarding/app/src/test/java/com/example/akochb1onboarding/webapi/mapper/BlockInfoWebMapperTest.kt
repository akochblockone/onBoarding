package com.example.akochb1onboarding.webapi.mapper

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Test

class BlockInfoWebMapperTest {

    private val mapper = BlockInfoWebMapper()

    @Test
    fun transform() {
        val block = mapper.transform(getJsonBlock())
        assert(block != null)
        block ?: return
        assert(block.id == ID)
        assert(block.blockNum == BLOCK_NUM)
        assert(block.producer == PRODUCER)
        assert(block.producerSignature == PRODUCER_SIGNATURE)
        assert(block.previousBlock == PREV_BLOCK)
        assert(block.transactionNumber == TRANSACTION_NUMBER)
        assert(block.fullBlock == getJsonBlock().toString())
        assert(block.creationTime == CREATION_TIME)
    }

    private fun getJsonBlock(): JsonObject {
        val transactions = JsonArray()
        transactions.add(1)
        transactions.add(2)
        val json = JsonObject()
        json.addProperty(BlockInfoWebMapper.BLOCK_NUM_JSON_FIELD_NAME, ID)
        json.addProperty(BlockInfoWebMapper.PRODUCER_JSON_FIELD_NAME, PRODUCER)
        json.addProperty(BlockInfoWebMapper.PRODUCER_SIGNATURE_JSON_FIELD_NAME, PRODUCER_SIGNATURE)
        json.addProperty(BlockInfoWebMapper.PREVIOUS_BLOCK_JSON_FIELD_NAME, PREV_BLOCK)
        json.add(BlockInfoWebMapper.TRANSACTIONS_JSON_FIELD_NAME, transactions)
        json.addProperty(BlockInfoWebMapper.TIMESTAMP_JSON_FIELD_NAME, CREATION_TIME)

        return json
    }

    companion object {
        private const val ID = "1234"
        private const val PRODUCER = "PRODUCER"
        private const val PRODUCER_SIGNATURE = "PRODUCER_SIGNATURE"
        private const val PREV_BLOCK = "PREV_BLOCK"
        private const val TRANSACTION_NUMBER = 2
        private const val CREATION_TIME = "CREATION_TIME"
        private const val BLOCK_NUM = 1234
    }
}