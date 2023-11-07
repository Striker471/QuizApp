package com.example.quizapp.presentation.auth_graph.register

sealed class RegisterEvent{
    data class EnteredUserName(val value: String) : RegisterEvent()
    data class EnteredEmail(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    data class EnteredRepeatPassword(val value: String) : RegisterEvent()
    object SignUp : RegisterEvent()

}
