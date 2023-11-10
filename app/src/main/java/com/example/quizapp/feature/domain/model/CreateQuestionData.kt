package com.example.quizapp.feature.domain.model

import android.net.Uri


data class CreateQuestionData(
    val imageUrl: String? = null,
    val questionDescription: String,
    val answers: List<String>,
    val correctAnswerIndex: Int ,
    val selectedTime: Int ,
    val questionId: String
)