package com.example.quizapp.presentation.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.use_case.auth.ResetPassword
import com.example.quizapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel
@Inject constructor(
    private val resetPassword: ResetPassword
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    private val _state = mutableStateOf(ResetPasswordState())
    val state: State<ResetPasswordState> = _state

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(
            email = value
        )
    }

    fun onResetPasswordClick() {
        resetPassword(_state.value.email).onEach {
            when (it) {
                is Resource.Error -> {
                    _eventFlow.emit(it.message ?: "Unknown error")
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _eventFlow.emit("Email has been sent successfully")
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
                // mozna context.getString(), pytanie czy architektonicznie bedzie poprawne
                // plus gorsze testy
            }
        }.launchIn(viewModelScope)

    }


}