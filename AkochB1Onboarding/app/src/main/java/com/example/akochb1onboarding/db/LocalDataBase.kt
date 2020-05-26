package com.example.akochb1onboarding.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.akochb1onboarding.db.dao.BlockDao
import com.example.akochb1onboarding.db.entity.BlockEntity

@Database(entities = [BlockEntity::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun blockDao(): BlockDao
}