package com.example.quizapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quizapp.feature_repository.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state : State<LoginState> = _state



    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EnteredEmail -> {
                _state.value = _state.value.copy(email = event.value)
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = _state.value.copy(password = event.value)
            }
            is LoginEvent.FacebookSignIn -> {
                TODO()
            }
            is LoginEvent.GoogleSignIn -> {
                TODO()
            }
            is LoginEvent.SignIn -> {
                TODO()
            }
        }
    }
}