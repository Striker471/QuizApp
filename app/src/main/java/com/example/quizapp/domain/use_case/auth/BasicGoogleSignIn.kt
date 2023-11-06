package com.example.quizapp.domain.use_case.auth

import android.content.Intent
import com.example.quizapp.feature_repository.data.repository.GoogleAuthRepository
import javax.inject.Inject

class BasicGoogleSignIn @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository
) {
    operator fun invoke(): Intent {
        return googleAuthRepository.getGoogleSignInIntent()
    }
}