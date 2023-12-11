package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.menu_quiz.MenuQuizState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuiz @Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(quizId: String): Flow<Resource<MenuQuizState>> = flow {
        emit(Resource.Loading)

        val quizDto = repository.getQuiz(quizId)
        val menuQuizState = MenuQuizState(
            title = quizDto.title,
            imageUrl = quizDto.imageUrl,
            author = quizDto.userName,
            description = quizDto.description,
            quizId = quizDto.quizId,
            views = quizDto.views
        )
        emit(Resource.Success(menuQuizState))

    }.catch(exceptionHandler::handle)
}