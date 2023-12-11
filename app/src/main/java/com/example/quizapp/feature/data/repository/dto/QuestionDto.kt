package com.example.quizapp.feature.data.repository.dto


data class QuestionDto(
    val imageUrl: String? = null,
    val questionDescription: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswerIndex: Int = -1,
    val selectedTime: Int = -1,
    val questionId: String = "",
    val attempts: Int = 0,
    val correctAttempts: Int = 0
)
