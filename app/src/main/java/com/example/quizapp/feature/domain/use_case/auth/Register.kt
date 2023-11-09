package com.example.quizapp.feature.domain.use_case.auth

import com.example.quizapp.feature.domain.model.RegisterData
import com.example.quizapp.feature.domain.model.RegisterRepositoryData
import com.example.quizapp.feature.domain.use_case.ValidationResult
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Register @Inject constructor(
    private val authRepository: AuthRepository,
    private val exceptionHandler: ExceptionHandler,
    private val registerValidation: RegisterValidation
) {
    operator fun invoke(registerData: RegisterData): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        when (val validationResult = registerValidation(registerData)) {

            is ValidationResult.Success -> {
                val registerRepositoryData = RegisterRepositoryData(
                    userName = registerData.userName,
                    email = registerData.email,
                    password = registerData.password
                )
                authRepository.register(registerRepositoryData)
                emit(Resource.Success(Unit))
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(message = validationResult.message))
            }
        }
    }.catch(exceptionHandler::handle)

}
