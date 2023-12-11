package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.model.CreateQuestionToRepositoryData
import com.example.quizapp.feature.domain.model.QuestionReturnData
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.use_case.ValidationResult
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

    class AddQuestion @Inject constructor(
        private val addQuestionValidation: AddQuestionValidation,
        private val repository: Repository,
        private val exceptionHandler: ExceptionHandler
    ) {
    
        operator fun invoke(
            createQuestionItem: CreateQuestionItem,
            quizId: String
        ): Flow<Resource<QuestionReturnData>> = flow {
            emit(Resource.Loading)
            when (val validationResult = addQuestionValidation(createQuestionItem)) {
    
                is ValidationResult.Success -> {
    
                    val questionReturnData = repository.addQuestion(
                        CreateQuestionToRepositoryData(
                            image = createQuestionItem.image,
                            questionDescription = createQuestionItem.questionDescription,
                            answers = createQuestionItem.answers,
                            correctAnswerIndex = createQuestionItem.correctAnswerIndex,
                            selectedTime = createQuestionItem.selectedTime
                        ),
                        quizId
                    )
                    emit(Resource.Success(questionReturnData))
                }
    
                is ValidationResult.Error -> {
                    emit(Resource.Error(validationResult.message))
                }
            }
        }.catch(exceptionHandler::handle)
    }