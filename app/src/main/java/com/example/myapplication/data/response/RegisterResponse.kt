package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("errors")
	val errors: List<ErrorsItem?>? = null
)

data class ErrorsItem(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
