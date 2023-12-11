package com.example.quizapp.feature.domain.use_case.auth

import com.example.quizapp.feature.domain.model.LoginData
import com.example.quizapp.feature.domain.use_case.ValidationResult
import javax.inject.Inject

class LoginValidation
@Inject constructor() {

    operator fun invoke(loginData: LoginData): ValidationResult {

        if (loginData.email.isBlank())
            return ValidationResult.Error(EMAIL_IS_EMPTY)
        if (loginData.password.isBlank())
            return ValidationResult.Error(PASSWORD_IS_EMPTY)
        return ValidationResult.Success
    }

    companion object {
        const val EMAIL_IS_EMPTY = "Given email is empty"
        const val PASSWORD_IS_EMPTY = "Given password is empty"
    }
}
