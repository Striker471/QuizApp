package com.example.quizapp.domain.use_case.auth

import android.content.IntentSender
import com.example.quizapp.domain.util.Resource
import com.example.quizapp.domain.util.error.ExceptionHandler
import com.example.quizapp.feature_repository.data.repository.GoogleAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OneTapGoogleSignIn @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(): Flow<Resource<IntentSender?>> = flow {
        emit(Resource.Loading())

        val intentSender = googleAuthRepository.beginOneTapSignIn()

        emit(Resource.Success(intentSender))

    }.catch(exceptionHandler::handle)


}