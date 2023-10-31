package com.example.quizapp.presentation.register

data class RegisterState(
    val userName : String = "",
    val email : String = "",
    val password : String = "",
    val repeatPassword : String = ""
)
