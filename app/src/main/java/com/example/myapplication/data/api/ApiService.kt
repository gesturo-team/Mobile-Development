package com.example.myapplication.data.api

import com.example.myapplication.data.response.AlphabetQuestionsItem
import com.example.myapplication.data.response.AlphabetQuizResponse
import com.example.myapplication.data.response.AlphabetResponse
import com.example.myapplication.data.response.DetailAlphabetResponse
import com.example.myapplication.data.response.DetailNumberResponse
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.NumberResponse
import com.example.myapplication.data.response.RegisterResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("register")
    suspend fun register(
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): RegisterResponse

//    {
//        "firstName": "John",
//        "lastName": "Doe",
//        "email": "john.doe@example.com",
//        "password": "securepassword"
//    }

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

    @GET("dictionary/number/{value}")
    suspend fun getNumberDetail(@Path("value") value: String): DetailNumberResponse

    @GET("dictionary/number")
    suspend fun getNumber(): NumberResponse

    @GET("/quizzes/type/alphabet")
    suspend fun getQuizAlphabet(): AlphabetQuizResponse

    @POST("/quizzes")
    fun submitAnswers(@Body answers: List<AlphabetQuestionsItem>): Call<ResponseBody>

}