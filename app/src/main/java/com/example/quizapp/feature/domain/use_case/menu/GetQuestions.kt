package com.example.quizapp.feature.domain.use_case.menu

import androidx.compose.runtime.mutableStateListOf
import com.example.quizapp.feature.data.mappers.toQuizData
import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionItem
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionState
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.SolveQuizQuestionData
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.SolveQuizState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestions  @Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(quizId: String): Flow<Resource<CreateQuestionState>> = flow {
        emit(Resource.Loading)

        val quizWithQuestions = repository.getQuestionsOfQuiz(quizId)

        val questionQuizData = quizWithQuestions.questions.map {
            it.toCreateQuestionItem()
        }
        val mutableQuestionQuizData = questionQuizData.toMutableList()
        mutableQuestionQuizData.add(CreateQuestionItem())
        val createQuestionState = CreateQuestionState(
            createQuestionStateList = mutableQuestionQuizData,
            currentQuestion = mutableQuestionQuizData.size
        )
        emit(Resource.Success(createQuestionState))

    }.catch(exceptionHandler::handle)

    private fun QuestionDto.toCreateQuestionItem(): CreateQuestionItem {

        val snapshotStateAnswers = mutableStateListOf<String>().apply {
            addAll(answers)
        }
        return CreateQuestionItem(
            imageUrl = imageUrl,
            questionDescription = questionDescription,
            answers = snapshotStateAnswers,
            correctAnswerIndex = correctAnswerIndex,
            selectedTime = selectedTime,
            questionId = questionId
        )
    }

}

