package com.example.quizapp.domain.use_case.auth

import com.example.quizapp.domain.model.LoginData
import com.example.quizapp.domain.use_case.ValidationResult
import com.example.quizapp.domain.util.Resource
import com.example.quizapp.domain.util.error.ExceptionHandler
import com.example.quizapp.feature_repository.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Login @Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val authRepository: AuthRepository,
    private val loginValidation: LoginValidation
) {

    operator fun invoke(loginData: LoginData): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        when (val validationResult = loginValidation(loginData)) {

            is ValidationResult.Success -> {
                authRepository.login(loginData)
                emit(Resource.Success(Unit))
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(message = validationResult.message))
            }
        }
    }.catch(exceptionHandler::handle)
}