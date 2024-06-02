package com.example.myapplication.data.pref

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){

    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        if (response.success!!) {
            response.authResult?.token?.let { token ->
                userPreferences.saveSession(UserModel(email, token, true))
            }
        }
        return response
    }

    //repo lain
//    fun getSession(): Flow<UserModel> {
//        return userPreferences.getSession()
//    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferences
        ) = UserRepository(apiService, userPreference)
    }
}