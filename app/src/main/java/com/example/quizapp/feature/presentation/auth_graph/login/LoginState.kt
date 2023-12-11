package com.example.quizapp.feature.presentation.auth_graph.login

data class LoginState(
    val email: String = "",
    val password : String = "",
    val isLoading: Boolean = false
)
