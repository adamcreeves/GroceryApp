package com.example.glossaryapp.models



data class User(
    val _id: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val mobile: String? = null,
    val password: String? = null,
    val token: String? = null
)

data class LoginResponse(
    val token: String,
    val user: User
)