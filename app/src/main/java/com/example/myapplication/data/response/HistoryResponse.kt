package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: HistoryData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class QuizItem(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("questions")
	val questions: List<QuestionsItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class HistoryData(

	@field:SerializedName("quiz")
	val quiz: List<QuizItem?>? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class QuestionsItem(

	@field:SerializedName("userAnswer")
	val userAnswer: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("answers")
	val answers: List<AnswersItem?>? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)

data class AnswersItem(

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("value")
	val value: String? = null
)
