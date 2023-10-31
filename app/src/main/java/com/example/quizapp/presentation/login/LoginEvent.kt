package com.example.quizapp.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object GoogleSignIn : LoginEvent()
    object FacebookSignIn : LoginEvent()
    object SignIn : LoginEvent()

}