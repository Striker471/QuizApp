package com.example.quizapp.feature.domain.model


data class CreateQuestionDto(
    val imageUrl: String? = null,
    val questionDescription: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswerIndex: Int = -1,
    val selectedTime: Int = -1,
    val questionId: String = ""
)