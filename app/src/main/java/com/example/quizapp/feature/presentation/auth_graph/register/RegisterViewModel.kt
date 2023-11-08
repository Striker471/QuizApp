package com.example.quizapp.feature.presentation.auth_graph.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.model.RegisterData
import com.example.quizapp.feature.domain.use_case.auth.Register
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val register: Register
) : ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow
    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredUserName -> {
                _state.value = _state.value.copy(
                    userName = event.value
                )
            }

            is RegisterEvent.EnteredEmail -> {
                _state.value = _state.value.copy(
                    email = event.value
                )
            }

            is RegisterEvent.EnteredPassword -> {
                _state.value = _state.value.copy(
                    password = event.value
                )
            }

            is RegisterEvent.EnteredRepeatPassword -> {
                _state.value = _state.value.copy(
                    repeatPassword = event.value
                )
            }

            is RegisterEvent.SignUp -> {
                val registerData = RegisterData(
                    userName = state.value.userName,
                    email = state.value.email,
                    password = state.value.password,
                    repeatPassword = state.value.repeatPassword
                )
                register(registerData).onEach {
                    when (it) {
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar("Succeeded registration"))
                            _state.value = RegisterState()
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(it.message ?: "Unknown error"))
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
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}