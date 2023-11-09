package com.example.quizapp.feature.presentation.auth_graph.login

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.model.LoginData
import com.example.quizapp.feature.domain.use_case.auth.LoginUseCases
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginUseCases: LoginUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.value = _state.value.copy(email = event.value)
            }

            is LoginEvent.EnteredPassword -> {
                _state.value = _state.value.copy(password = event.value)
            }

            is LoginEvent.GoogleSignInClick -> {
                loginUseCases.oneTapGoogleSignIn().onEach {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { intentSender ->
                                _eventFlow.emit(UiEvent.LaunchOneTapSignIn(intentSender))
                            } ?: run {
                                loginUseCases.basicGoogleSignIn().let { intent ->
                                    _eventFlow.emit(UiEvent.BasicGoogleSignIn(intent))
                                }
                            }
                        }

                        is Resource.Error -> {
                            loginUseCases.basicGoogleSignIn().let { intent ->
                                _eventFlow.emit(UiEvent.BasicGoogleSignIn(intent))
                            }
                        }

                        is Resource.Loading -> {

                        }

                    }
                }.launchIn(viewModelScope)
            }

            is LoginEvent.SignIn -> {
                loginUseCases.login(
                    LoginData(
                        email = _state.value.email, password = _state.value.password
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

            is LoginEvent.OneTapSignIn -> {

                loginUseCases.completeOneTapSignIn(event.intent).onEach {
                    when (it) {
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.MenuNavigate)
                        }
                    }
                }.launchIn(viewModelScope)

            }

            is LoginEvent.StandardGoogleSignIn -> {
                loginUseCases.completeGoogleSignIn(event.intent).onEach {
                    when (it) {
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.MenuNavigate)
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