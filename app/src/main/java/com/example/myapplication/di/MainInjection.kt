package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.api.ApiConfig
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.data.pref.UserPreferences
import com.example.myapplication.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object MainInjection {
    fun provideRepository(context: Context): MainRepository {
        val pref  = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return MainRepository.getInstance(apiService, pref)
    }
}