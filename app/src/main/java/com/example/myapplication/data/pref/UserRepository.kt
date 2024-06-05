//package com.example.myapplication.data.pref
//
//import com.example.myapplication.data.api.ApiService
//import com.example.myapplication.data.model.UserModel
//import com.example.myapplication.data.response.LoginResponse
//import kotlinx.coroutines.flow.Flow
//
//class UserRepository private constructor(
//    private val apiService: ApiService,
//    private val userPreferences: UserPreferences
//){
//
//    suspend fun saveSession(userModel: UserModel) {
//        userPreferences.saveSession(userModel)
//    }
//
//    suspend fun login(email: String, password: String): LoginResponse {
//        val response = apiService.login(email, password)
//        if (response.success!!) {
//            response.authResult?.token?.let { token ->
//                userPreferences.saveSession(UserModel(email, token, true))
//            }
//        }
//        return response
//    }
//
//    //repo lain
////    fun getSession(): Flow<UserModel> {
////        return userPreferences.getSession()
////    }
//
//    companion object {
//        fun getInstance(
//            apiService: ApiService,
//            userPreference: UserPreferences
//        ) = UserRepository(apiService, userPreference)
//    }
//}
package com.example.myapplication.data.pref

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.response.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){

    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val emailRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), email)
        val passwordRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), password)

        try {
            val response = apiService.login(emailRequestBody, passwordRequestBody)
            if (response.success!!) {
                response.authResult?.token?.let { token ->
                    userPreferences.saveSession(UserModel(email, token, true))
                }
            }
            return response
        } catch (e: HttpException) {
            // Handle the HTTP exception as needed
            throw e
        }
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
