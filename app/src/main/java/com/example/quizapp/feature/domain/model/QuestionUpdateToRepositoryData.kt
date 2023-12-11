package com.example.quizapp.feature.domain.model

import android.net.Uri

data class QuestionUpdateToRepositoryData(
    val image: Uri? = null,
    val questionDescription: String? = null,
    val answers: List<String>? = null,
    val correctAnswerIndex: Int? = null,
    val selectedTime: Int? = null
)
