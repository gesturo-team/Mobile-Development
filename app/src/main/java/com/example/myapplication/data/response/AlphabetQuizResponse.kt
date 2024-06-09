package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class AlphabetQuizResponse(

	@field:SerializedName("data")
	val data: AlphabetData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class AlphabetAnswersItem(

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("value")
	val value: String? = null
)

data class AlphabetQuestionsItem(

	var selectedAnswer: Int = -1,

	@field:SerializedName("userAnswer")
	val userAnswer: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("answers")
	val answers: List<AlphabetAnswersItem?>? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)

data class AlphabetData(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("questions")
	val questions: List<AlphabetQuestionsItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
