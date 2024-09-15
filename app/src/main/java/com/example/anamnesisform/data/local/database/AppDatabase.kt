package com.example.anamnesisform.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anamnesisform.data.local.dao.AnamnesisFormDao
import com.example.anamnesisform.domain.model.AnamnesisForm

@Database(entities = [AnamnesisForm::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun formDao(): AnamnesisFormDao

    companion object {
        @Volatile
        private var database: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return database ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                database = instance
                instance
            }
        }
    }
}