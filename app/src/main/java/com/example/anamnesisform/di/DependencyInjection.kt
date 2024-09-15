package com.example.anamnesisform.di

import com.example.anamnesisform.data.local.database.AppDatabase
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepository
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepositoryImpl
import com.example.anamnesisform.features.createForm.presentation.FormFragmentViewModel
import com.example.anamnesisform.features.formsList.presentation.FormListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { AppDatabase.getDatabase(get()) }
    single { get<AppDatabase>().formDao() }
    single<AnamnesisFormRepository> { AnamnesisFormRepositoryImpl(get()) }

    viewModel { FormFragmentViewModel(get()) }
    viewModel { FormListViewModel(get()) }
}