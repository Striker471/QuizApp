package com.example.quizapp.domain.use_case.auth

import com.example.quizapp.domain.model.RegisterData
import com.example.quizapp.domain.use_case.ValidationResult
import javax.inject.Inject

class RegisterValidation
@Inject constructor() {

    operator fun invoke(registerData: RegisterData): ValidationResult {

        if (registerData.password.isEmpty() || registerData.repeatPassword.isEmpty()) {
            return ValidationResult.Error(ERROR_EMPTY_PASSWORDS)
        }
        if (registerData.password != registerData.repeatPassword) {
            return ValidationResult.Error(ERROR_DIFFERENT_PASSWORDS)
        }
        return ValidationResult.Success
    }

    companion object {
        const val ERROR_EMPTY_PASSWORDS = "Given passwords are empty"
        const val ERROR_DIFFERENT_PASSWORDS = "Given passwords are different"
    }
}