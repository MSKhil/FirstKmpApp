package com.example.kmpapp.di

import com.example.kmpapp.data.UserPreferencesRepository
import com.example.kmpapp.ui.login.LoginViewModel
import com.example.kmpapp.network.KtorOneGalaxyApi
import org.koin.core.module.Module
import org.koin.dsl.module


expect fun platformModule(): Module

val commonModule = module {
    single { KtorOneGalaxyApi() }
    single { UserPreferencesRepository(get()) }

    factory { LoginViewModel(get(), get()) }
}