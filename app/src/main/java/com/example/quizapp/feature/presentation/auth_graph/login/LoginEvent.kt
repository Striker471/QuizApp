package com.example.quizapp.feature.presentation.auth_graph.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : com.example.quizapp.feature.presentation.auth_graph.login.LoginEvent()
    data class EnteredPassword(val value: String) : com.example.quizapp.feature.presentation.auth_graph.login.LoginEvent()
    object GoogleSignInClick : com.example.quizapp.feature.presentation.auth_graph.login.LoginEvent()
    object SignIn : com.example.quizapp.feature.presentation.auth_graph.login.LoginEvent()
    data class OneTapSignIn(val intent: Intent) : com.example.quizapp.feature.presentation.auth_graph.login.LoginEvent()
    data class StandardGoogleSignIn(val intent: Intent) : com.example.quizapp.feature.presentation.auth_graph.login.LoginEvent()

}