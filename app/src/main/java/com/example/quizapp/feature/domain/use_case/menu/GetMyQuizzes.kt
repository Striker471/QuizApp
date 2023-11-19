package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.data.mappers.toQuizData
import com.example.quizapp.feature.domain.model.QuizData
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyQuizzes @Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<List<QuizData>>> = flow {
        emit(Resource.Loading)

        val quizDtoList = repository.getMyQuizzes()

        val quizDataList = quizDtoList.map {
            it.toQuizData()
        }
        emit(Resource.Success(quizDataList))

    }.catch(exceptionHandler::handle)

}