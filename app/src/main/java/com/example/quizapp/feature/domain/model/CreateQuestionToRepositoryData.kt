package com.example.quizapp.feature.domain.model

import android.net.Uri

data class CreateQuestionToRepositoryData(
    val image: Uri? = null,
    val questionDescription: String,
    val answers: List<String>,
    val correctAnswerIndex: Int ,
    val selectedTime: Int ,
)
