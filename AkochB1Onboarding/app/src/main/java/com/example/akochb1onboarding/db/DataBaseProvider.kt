package com.example.akochb1onboarding.db

import android.content.Context
import androidx.room.Room

object DataBaseProvider {
    private const val DATABASE_FILE_NAME = "b1-database.db"
    private lateinit var db: LocalDataBase

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            LocalDataBase::class.java, DATABASE_FILE_NAME
        ).build()
    }

    fun getDataBase(): LocalDataBase = db
}