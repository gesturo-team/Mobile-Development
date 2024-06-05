package com.example.myapplication.data.api

import com.example.myapplication.data.response.AlphabetResponse
import com.example.myapplication.data.response.DetailAlphabetResponse
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.NumberResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("login")
    suspend fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): LoginResponse

    @GET("dictionary/alphabet")
    suspend fun getAlphabet(): AlphabetResponse

    @GET("dictionary/alphabet/{value}")
    suspend fun getAlphabetDetail(@Path("value") value: String): DetailAlphabetResponse

    @GET("dictionary/number")
    suspend fun getNumber(): NumberResponse

}