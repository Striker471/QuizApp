package com.example.quizapp.presentation.login

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.model.LoginData
import com.example.quizapp.domain.use_case.auth.Login
import com.example.quizapp.domain.util.Resource
import com.example.quizapp.feature_repository.data.repository.GoogleAuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val login: Login,
    private val googleAuthRepository: GoogleAuthRepository
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow


    init {
        viewModelScope.launch {
            googleAuthRepository.signOut()
        }
    }

    fun onSignInResult(intent: Intent) {
        viewModelScope.launch {
            googleAuthRepository.signInWithIntent(intent)
            // aktualizacja UI lub nawigacja po pomyślnym logowaniu
        }
    }
    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                // Następnie możesz użyć account.getIdToken() i przekazać token do Firebase lub innego systemu backend.
                googleAuthRepository.signInWithGoogleAccount(account)
            } catch (e: ApiException) {
                // Obsłuż wyjątek, może być konieczne wyświetlenie komunikatu o błędzie.
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
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
                viewModelScope.launch {
//                    googleAuthRepository.signIn()?.let { intentSender ->
//                        _eventFlow.emit(UiEvent.LaunchOneTapSignIn(intentSender))
//                    }
                    googleAuthRepository.signInWithGoogle().let{
                        _eventFlow.emit(UiEvent.BasicGoogleSignIn(it))
                    }
                }
            }

            is LoginEvent.SignIn -> {
                login(
                    LoginData(
                        email = _state.value.email,
                        password = _state.value.password
                    )
                ).onEach {
                    when (it) {
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.MenuNavigate
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    it.message ?: "Unknown error"
                                )
                            )
                            _state.value = _state.value.copy(
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    sealed class UiEvent {
        object MenuNavigate : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
        data class LaunchOneTapSignIn(val intentSender: IntentSender) : UiEvent()
        data class BasicGoogleSignIn(val intent: Intent) : UiEvent()
    }
}