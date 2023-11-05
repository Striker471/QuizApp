package com.example.quizapp.presentation.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quizapp.domain.use_case.auth.ResetPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel
@Inject constructor(
    private val resetPassword: ResetPassword
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onResetPasswordClick() {


    }


}