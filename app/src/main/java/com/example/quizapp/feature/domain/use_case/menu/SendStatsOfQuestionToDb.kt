package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.model.SendQuestionStatsData
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendStatsOfQuestionToDb @Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(sendQuestionStatsData: SendQuestionStatsData): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        repository.sendStatsOfQuestionToDb(sendQuestionStatsData)
        emit(Resource.Success(Unit))

    }.catch(exceptionHandler::handle)
}