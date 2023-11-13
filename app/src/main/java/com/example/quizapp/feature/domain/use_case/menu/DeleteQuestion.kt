package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteQuestion @Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val repository: Repository,
) {

    operator fun invoke(quizId: String, questionId: String?): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        repository.deleteQuestion(quizId, questionId!!)
        emit(Resource.Success(Unit))

    }.catch(exceptionHandler::handle)
}