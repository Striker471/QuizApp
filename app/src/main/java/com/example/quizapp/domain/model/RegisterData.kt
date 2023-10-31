package com.example.quizapp.domain.model

data class RegisterData(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
