package com.example.anamnesisform.data.local.repository

import com.example.anamnesisform.data.local.dao.AnamnesisFormDao
import com.example.anamnesisform.domain.model.AnamnesisForm

class AnamnesisFormRepositoryImpl(private val dao: AnamnesisFormDao) : AnamnesisFormRepository {
    override suspend fun getAllForms(): List<AnamnesisForm> {
        return dao.getAll()
    }

    override suspend fun insertForm(form: AnamnesisForm) {
        dao.insert(form)
    }

    override suspend fun updateForm(form: AnamnesisForm) {
        dao.update(form)
    }

    override suspend fun deleteForm(form: AnamnesisForm) {
        dao.delete(form)
    }
}