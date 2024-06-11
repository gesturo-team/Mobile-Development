package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class SubmitAlphabetResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
