package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class SubmitAlphabetResponse(

	@field:SerializedName("quiz")
	val quiz: SubmitAlphabetQuiz? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class SubmitAlphabetItem(

	@field:SerializedName("userAnswer")
	val userAnswer: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("answers")
	val answers: List<SubmitAlphabetAnswersItem?>? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)

data class SubmitAlphabetAnswersItem(

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("value")
	val value: String? = null
)

data class SubmitAlphabetQuiz(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("questions")
	val questions: List<SubmitAlphabetItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
