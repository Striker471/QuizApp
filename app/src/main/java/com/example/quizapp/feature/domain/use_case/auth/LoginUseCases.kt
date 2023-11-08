package com.example.quizapp.feature.domain.use_case.auth

import javax.inject.Inject

class LoginUseCases @Inject constructor(
    val login: Login,
    val oneTapGoogleSignIn: OneTapGoogleSignIn,
    val basicGoogleSignIn: BasicGoogleSignIn,
    val completeOneTapSignIn: CompleteOneTapSignIn,
    val completeGoogleSignIn: CompleteGoogleSignIn
)
