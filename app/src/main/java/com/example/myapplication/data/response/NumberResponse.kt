package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class NumberResponse(

	@field:SerializedName("data")
	val data: NumberData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class NumberListItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)

data class NumberData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("wordList")
	val wordList: List<NumberListItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
