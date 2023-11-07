package com.example.quizapp.presentation.auth_graph.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object GoogleSignInClick : LoginEvent()
    object SignIn : LoginEvent()
    data class OneTapSignIn(val intent: Intent) : LoginEvent()
    data class StandardGoogleSignIn(val intent: Intent) : LoginEvent()

}