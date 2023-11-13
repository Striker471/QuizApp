package com.example.quizapp.feature.domain.use_case.auth

import android.content.Intent
import com.example.quizapp.feature.data.repository.impl.GoogleAuthRepository
import javax.inject.Inject

class BasicGoogleSignIn @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository
) {
    operator fun invoke(): Intent {
        return googleAuthRepository.getGoogleSignInIntent()
    }
}