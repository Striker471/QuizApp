package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.data.repository.Repository
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateQuiz @Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val repository: Repository
) {

    operator fun invoke(createQuizData: CreateQuizData) : Flow<Resource<String>> = flow{
        emit(Resource.Loading)

        val quizId = repository.createQuiz(createQuizData)

        emit(Resource.Success(quizId))
    }.catch(exceptionHandler::handle)

}