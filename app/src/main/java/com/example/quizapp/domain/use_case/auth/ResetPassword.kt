package com.example.quizapp.domain.use_case.auth

import com.example.quizapp.domain.util.Resource
import com.example.quizapp.domain.util.error.ExceptionHandler
import com.example.quizapp.feature_repository.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResetPassword @Inject constructor(
    private val authRepository: AuthRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        authRepository.resetPassword(email)

        emit(Resource.Success(Unit))

    }.catch(exceptionHandler::handle)
}