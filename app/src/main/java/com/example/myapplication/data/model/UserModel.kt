package com.example.myapplication.data.model


data class UserModel(
    val email: String,
    val token: String,
    val isLoggedIn: Boolean,
    val fullName: String? = null
)

