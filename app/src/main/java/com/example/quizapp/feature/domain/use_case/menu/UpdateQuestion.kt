package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.data.repository.Repository
import com.example.quizapp.feature.domain.model.CreateQuestionToRepositoryData
import com.example.quizapp.feature.domain.model.QuestionUpdateToRepositoryData
import com.example.quizapp.feature.domain.use_case.ValidationResult
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateQuestion @Inject constructor(
    private val addQuestionValidation: AddQuestionValidation,
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(
        oldItem: CreateQuestionItem,
        newItem: CreateQuestionItem,
        quizId: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        when (val validationResult = addQuestionValidation(newItem)) {
            is ValidationResult.Success -> {
                val questiontoUpdate = QuestionUpdateToRepositoryData(
                    image = newItem.image,
                    questionDescription = if (oldItem.questionDescription != newItem.questionDescription) newItem.questionDescription else null,
                    answers = if (oldItem.answers != newItem.answers) newItem.answers else null,
                    correctAnswerIndex = if (oldItem.correctAnswerIndex != newItem.correctAnswerIndex) newItem.correctAnswerIndex else null,
                    selectedTime = if (oldItem.selectedTime != newItem.selectedTime) newItem.selectedTime else null
                )
                newItem.questionId?.let { repository.updateQuestion(questiontoUpdate, quizId, it) }
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(validationResult.message))
            }

        }

    }
}