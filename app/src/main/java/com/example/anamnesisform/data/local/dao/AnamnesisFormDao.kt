package com.example.anamnesisform.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.anamnesisform.domain.model.AnamnesisForm

@Dao
interface AnamnesisFormDao {
    @Query("SELECT * FROM anamnesisform")
    suspend fun getAll(): List<AnamnesisForm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(form: AnamnesisForm)

    @Update
    suspend fun update(form: AnamnesisForm)

    @Delete
    suspend fun delete(form: AnamnesisForm)
}