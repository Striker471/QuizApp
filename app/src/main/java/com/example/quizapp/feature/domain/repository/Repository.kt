package com.example.quizapp.feature.domain.repository

import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.domain.model.CreateQuestionToRepositoryData
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.model.QuestionReturnData
import com.example.quizapp.feature.domain.model.QuestionUpdateToRepositoryData
import com.example.quizapp.feature.domain.model.QuizWithQuestions
import com.example.quizapp.feature.domain.model.SendQuestionStatsData
import com.google.firebase.firestore.DocumentSnapshot

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

    suspend fun getMostViewedQuizzes(): List<QuizDto>

    suspend fun getQuiz(quizId: String): QuizDto

    suspend fun getFirstPageOfQuizzes(): List<QuizDto>

    suspend fun getAnotherPageOfQuizzes(document: DocumentSnapshot): List<QuizDto>

    suspend fun getQuestionsOfQuiz(quizId: String): QuizWithQuestions

    suspend fun sendStatsOfQuestionToDb(data: SendQuestionStatsData)

    suspend fun completeQuiz(quizId: String, score: Int)

    suspend fun incrementQuizViews(quizId: String)
    suspend fun getMyQuizzes(): List<QuizDto>

}