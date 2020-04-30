package com.example.akochb1onboarding.db.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.akochb1onboarding.domain.entity.Block

@Entity(tableName = "block_table", indices = [Index(value = ["id"], unique = false)])
open class BlockEntity(
    @ColumnInfo(name = "id") var id: String? = null,
    @PrimaryKey @ColumnInfo(name = "block_num") var blockNum: Int? = null,
    @ColumnInfo(name = "producer") var producer: String? = null,
    @ColumnInfo(name = "producer_signature") var producerSignature: String? = null,
    @ColumnInfo(name = "previous_block") var previousBlock: String? = null,
    @ColumnInfo(name = "transaction_number") var transactionNumber: Int? = null,
    @ColumnInfo(name = "full_block") var fullBlock: String? = null,
    @ColumnInfo(name = "creation_time") var creationTime: String? = null
) {
    constructor(block: Block) :
            this(
                id = block.id,
                blockNum = block.blockNum,
                producer = block.producer,
                producerSignature = block.producerSignature,
                previousBlock = block.previousBlock,
                transactionNumber = block.transactionNumber,
                fullBlock = block.fullBlock,
                creationTime = block.creationTime
            )

    fun toBlock() = Block(
        id,
        blockNum,
        producer,
        producerSignature,
        previousBlock,
        transactionNumber,
        fullBlock,
        creationTime
    )

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BlockEntity>() {
            override fun areItemsTheSame(old: BlockEntity, new: BlockEntity): Boolean {
                return old.id === new.id
            }

            override fun areContentsTheSame(old: BlockEntity, new: BlockEntity): Boolean {
                return old.id?.equals(new.id) ?: false
            }
        }
    }
}