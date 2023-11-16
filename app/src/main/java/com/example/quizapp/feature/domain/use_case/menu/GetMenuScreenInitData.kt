package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.data.mappers.toQuizItem
import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.domain.model.QuizItem
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.menu.MenuScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMenuScreenInitData @Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(): Flow<Resource<MenuScreenState>> = flow {
        emit(Resource.Loading)

        val theLatest = repository.getTheLatestQuizzes()
        val mostViewed = repository.getMostViewedQuizzes()

        val combinedLists = MenuScreenState(
            theLatestList = theLatest.toScreenDataList(),
            mostPopularList = mostViewed.toScreenDataList()
        )
        emit(Resource.Success(combinedLists))

    }.catch(exceptionHandler::handle)


    private fun List<QuizDto>.toScreenDataList(): List<QuizItem> {
        return this.map {
            it.toQuizItem()
        }
    }
}