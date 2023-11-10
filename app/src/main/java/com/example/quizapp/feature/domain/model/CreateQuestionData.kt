package com.example.quizapp.feature.domain.model

import android.net.Uri


data class CreateQuestionData(
    val imageUrl: String? = null,
    val questionDescription: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswerIndex: Int = -1,
    val selectedTime: Int = -1,
    val questionId: String = ""
)