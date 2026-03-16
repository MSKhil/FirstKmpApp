package com.example.kmpapp.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmpapp.data.DataRepository
import com.example.kmpapp.data.UserPreferencesRepository
import com.example.kmpapp.ui.login.LoginViewModel
import com.example.kmpapp.network.KtorOneGalaxyApi
import com.example.kmpapp.ui.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.dsl.module


expect fun platformModule(): Module

val commonModule = module {
    single { KtorOneGalaxyApi() }
    single { UserPreferencesRepository(get()) }
    single { DataRepository(get(), get()) }

    factory { LoginViewModel(userPreferencesRepository = get(), apiService = get()) }
    factory { HomeViewModel(get(), get()) }
}