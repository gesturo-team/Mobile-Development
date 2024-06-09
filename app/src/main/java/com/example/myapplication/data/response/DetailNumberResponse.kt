package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class DetailNumberResponse(

	@field:SerializedName("data")
	val data: DetailNumber? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DetailNumber(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)
