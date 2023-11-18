package com.example.quizapp.feature.domain.model

data class QuestionData(
    val imageUrl: String? = null,
    val questionDescription: String,
    val answers: List<String>,
    val correctAnswerIndex: Int,
    val selectedTime: Int,
    val questionId: String,
    val attempts: Int,
    val correctAttempts: Int
)
