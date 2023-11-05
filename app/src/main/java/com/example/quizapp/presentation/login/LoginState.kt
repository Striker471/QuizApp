package com.example.quizapp.presentation.login

data class LoginState(
    val email: String = "",
    val password : String = "",
    val isLoading: Boolean = false
)
