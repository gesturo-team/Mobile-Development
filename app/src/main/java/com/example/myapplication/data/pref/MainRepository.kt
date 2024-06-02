package com.example.myapplication.data.pref

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.UserModel
import kotlinx.coroutines.flow.Flow

class MainRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences,
        ) = MainRepository(apiService, userPreferences)
    }
}