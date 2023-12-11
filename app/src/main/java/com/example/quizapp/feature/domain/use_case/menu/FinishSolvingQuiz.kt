package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FinishSolvingQuiz @Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(quizId: String, score: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        repository.completeQuiz(quizId, score)
        emit(Resource.Success(Unit))

    }.catch(exceptionHandler::handle)
}