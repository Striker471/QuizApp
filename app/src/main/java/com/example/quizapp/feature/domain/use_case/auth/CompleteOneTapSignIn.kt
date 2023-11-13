package com.example.quizapp.feature.domain.use_case.auth

import android.content.Intent
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.data.repository.impl.GoogleAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompleteOneTapSignIn @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(intent: Intent): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        googleAuthRepository.handleOneTapSignInWithIntent(intent)

        emit(Resource.Success(Unit))

    }.catch(exceptionHandler::handle)


}