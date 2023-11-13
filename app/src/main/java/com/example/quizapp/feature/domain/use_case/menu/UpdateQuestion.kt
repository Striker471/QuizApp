package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.data.repository.impl.RepositoryImpl
import com.example.quizapp.feature.domain.model.QuestionUpdateToRepositoryData
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.use_case.ValidationResult
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
    ): Flow<Resource<String?>> = flow {
        emit(Resource.Loading)
        when (val validationResult = addQuestionValidation(newItem)) {
            is ValidationResult.Success -> {
                val questionToUpdate = QuestionUpdateToRepositoryData(
                    image = newItem.image,
                    questionDescription = if (oldItem.questionDescription != newItem.questionDescription) newItem.questionDescription else null,
                    answers = if (oldItem.answers != newItem.answers) newItem.answers else null,
                    correctAnswerIndex = if (oldItem.correctAnswerIndex != newItem.correctAnswerIndex) newItem.correctAnswerIndex else null,
                    selectedTime = if (oldItem.selectedTime != newItem.selectedTime) newItem.selectedTime else null
                )
                val imageUrl = newItem.questionId?.let {
                    repository.updateQuestion(
                        questionToUpdate,
                        quizId,
                        it
                    )
                }
                emit(Resource.Success(imageUrl))
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(validationResult.message))
            }
        }
    }.catch(exceptionHandler::handle)
}