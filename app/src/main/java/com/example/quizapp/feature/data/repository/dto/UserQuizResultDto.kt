package com.example.quizapp.feature.data.repository.dto

import com.google.firebase.Timestamp

data class UserQuizResultDto(
    val userId: String = "",
    val quizId: String = "",
    val score: Int = 0,
    val completedAt: Timestamp = Timestamp.now()
)

data class UserAnswerDto(
    val questionId: String,
    val selectedAnswerIndex: Int,
    val isCorrect: Boolean
)