package com.example.myapplication.data.pref

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.response.AlphabetQuizResponse
import com.example.myapplication.data.response.AlphabetResponse
import com.example.myapplication.data.response.DetailAlphabetResponse
import com.example.myapplication.data.response.DetailNumberResponse
import com.example.myapplication.data.response.NumberResponse
import com.example.myapplication.ui.alphabet.DetailAlphabetActivity
import kotlinx.coroutines.flow.Flow

class MainRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun getAlphabet() : AlphabetResponse {
        return apiService.getAlphabet()
    }

    suspend fun getAlphabetDetail(value: String): DetailAlphabetResponse{
        return apiService.getAlphabetDetail(value)
    }

    suspend fun getNumber() : NumberResponse {
        return apiService.getNumber()
    }

    suspend fun getNumberDetail(value: String): DetailNumberResponse{
        return apiService.getNumberDetail(value)
    }

    suspend fun getAlphabetQuiz(): AlphabetQuizResponse {
        return apiService.getQuizAlphabet()
    }

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