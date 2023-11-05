package com.example.quizapp.domain.use_case.auth

import com.example.quizapp.feature_repository.data.repository.AuthRepository
import javax.inject.Inject

class IsUserSignedIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Boolean{
        return authRepository.getSignedInUser() != null
    }
}