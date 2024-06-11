package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class QuizResponse(

	@field:SerializedName("data")
	val data: QuizData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class QuizAnswersItem(

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("value")
	val value: String? = null
)

data class QuizQuestionsItem(

	@field:SerializedName("userAnswer")
	var userAnswer: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("answers")
	val answers: List<QuizAnswersItem?>? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)

data class QuizData(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("questions")
	val questions: List<QuizQuestionsItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
