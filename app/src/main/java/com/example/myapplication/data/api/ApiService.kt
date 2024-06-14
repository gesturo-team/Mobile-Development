package com.example.myapplication.data.api

import com.example.myapplication.data.response.QuizData
import com.example.myapplication.data.response.QuizResponse
import com.example.myapplication.data.response.AlphabetResponse
import com.example.myapplication.data.response.DetailAlphabetResponse
import com.example.myapplication.data.response.DetailNumberResponse
import com.example.myapplication.data.response.HistoryResponse
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.NumberResponse
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.data.response.SubmitAlphabetResponse
import okhttp3.RequestBody
import retrofit2.http.Body
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
    suspend fun getQuizAlphabet(): QuizResponse

    @GET("/quizzes/type/number")
    suspend fun getQuizNumber():QuizResponse

    @POST("/quizzes")
    suspend fun submitAnswers(@Body answers: QuizData): SubmitAlphabetResponse

    @GET("/quizzes/history")
    suspend fun getHistory() : HistoryResponse

}