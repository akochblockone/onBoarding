package com.example.akochb1onboarding.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.akochb1onboarding.db.entity.BlockEntity

@Dao
interface BlockDao {

    @Query("SELECT * FROM block_table")
    fun getAll(): List<BlockEntity>

    @Query("SELECT * FROM block_table where id = :blockId")
    fun getBlock(blockId: String): BlockEntity?

    @Query("SELECT * FROM block_table order by creation_time desc")
    fun getPaged(): DataSource.Factory<Int, BlockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(blockEntity: BlockEntity)
}