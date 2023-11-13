package com.example.quizapp.feature.domain.repository

import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.domain.model.CreateQuestionToRepositoryData
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.model.QuestionReturnData
import com.example.quizapp.feature.domain.model.QuestionUpdateToRepositoryData

interface Repository {

    suspend fun createQuiz(createQuizData: CreateQuizData): String

    suspend fun addQuestion(
        createQuestionToRepositoryData: CreateQuestionToRepositoryData,
        quizId: String
    ): QuestionReturnData

    suspend fun updateQuestion(
        questionData: QuestionUpdateToRepositoryData,
        quizId: String,
        questionId: String
    ): String?

    suspend fun deleteQuestion(quizId: String, questionId: String)

    suspend fun getTheLatestQuizzes(): List<QuizDto>

    suspend fun getMostViewedQuizzes() : List<QuizDto>

}