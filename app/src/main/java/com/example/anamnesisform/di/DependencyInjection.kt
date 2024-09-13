package com.example.anamnesisform.di

import com.example.anamnesisform.data.local.database.AppDatabase
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepository
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepositoryImpl
import com.example.anamnesisform.presentation.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { AppDatabase.getDatabase(get()) }
    single { get<AppDatabase>().formDao() }
    single<AnamnesisFormRepository> { AnamnesisFormRepositoryImpl(get()) }
    viewModel { MainActivityViewModel(get()) }
}