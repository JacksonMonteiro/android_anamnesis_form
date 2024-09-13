package com.example.anamnesisform.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.anamnesisform.domain.model.AnamnesisForm

@Dao
interface AnamnesisFormDao {
    @Query("SELECT * FROM anamnesisform")
    fun getAll(): List<AnamnesisForm>

    @Insert
    fun insert(form: AnamnesisForm)

    @Update
    fun update(form: AnamnesisForm)

    @Delete
    fun delete(form: AnamnesisForm)
}