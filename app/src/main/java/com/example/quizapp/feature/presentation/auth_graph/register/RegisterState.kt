package com.example.quizapp.feature.presentation.auth_graph.register

data class RegisterState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false
)
