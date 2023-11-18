package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.data.mappers.toQuizData
import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.SolveQuizQuestionData
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.SolveQuizState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsToSolve @Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(quizId: String): Flow<Resource<SolveQuizState>> = flow {
        emit(Resource.Loading)

        val quizWithQuestions = repository.getQuestionsOfQuiz(quizId)

        val randomQuestions = quizWithQuestions.questions.shuffled().take(10)

        val questionSolveQuizData = randomQuestions.map {
            it.toQuestionSolveQuizData()
        }
        val solveQuizState = SolveQuizState(
            questionList = questionSolveQuizData,
            quizData = quizWithQuestions.quiz.toQuizData()
        )
        emit(Resource.Success(solveQuizState))

    }.catch(exceptionHandler::handle)

    private fun QuestionDto.toQuestionSolveQuizData(): SolveQuizQuestionData {
        return SolveQuizQuestionData(
            imageUrl = imageUrl,
            questionDescription = questionDescription,
            answers = answers,
            correctAnswerIndex = correctAnswerIndex,
            selectedTime = selectedTime,
            questionId = questionId
        )
    }
}