package com.example.quizapp.feature.domain.use_case.auth

import com.example.quizapp.feature.data.repository.impl.AuthRepository
import javax.inject.Inject

class IsUserSignedIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Boolean{
        return authRepository.getSignedInUser() != null
    }
}