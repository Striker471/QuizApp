package com.example.quizapp.feature.domain.model

data class RegisterData(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
