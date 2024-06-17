package com.example.myapplication.data.pref

import android.util.Log
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.response.ErrorsItem
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.RegisterResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){

    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): RegisterResponse {
        return try {
            val firstNameReg = firstName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val lastNameReg = lastName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val emailReg = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordReg = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            apiService.register(firstNameReg, lastNameReg, emailReg, passwordReg)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val emailLogin = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordLogin = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        try {
            val response = apiService.login(emailLogin, passwordLogin)
            if (response.success!!) {
                response.authResult?.token?.let { token ->
                    userPreferences.saveSession(UserModel(email, token, true))
                }
            }
            return response
        } catch (e: HttpException) {
            throw e
        }
    }


    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferences
        ) = UserRepository(apiService, userPreference)
    }
}
