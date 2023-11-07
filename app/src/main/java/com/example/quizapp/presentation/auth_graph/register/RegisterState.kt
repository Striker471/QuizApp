package com.example.quizapp.presentation.auth_graph.register

data class RegisterState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false
)
