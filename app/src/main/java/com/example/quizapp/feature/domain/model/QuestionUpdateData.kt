package com.example.quizapp.feature.domain.model

data class QuestionUpdateData(
    val imageUrl: String? = null,
    val questionDescription: String? = null,
    val answers: List<String>? = null,
    val correctAnswerIndex: Int? = null,
    val selectedTime: Int? = null
)