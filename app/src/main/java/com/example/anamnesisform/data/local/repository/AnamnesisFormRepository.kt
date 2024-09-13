package com.example.anamnesisform.data.local.repository

import com.example.anamnesisform.domain.model.AnamnesisForm

interface AnamnesisFormRepository {
    suspend fun getAllForms(): List<AnamnesisForm>
    suspend fun insertForm(form: AnamnesisForm)
    suspend fun updateForm(form: AnamnesisForm)
    suspend fun deleteForm(form: AnamnesisForm)
}