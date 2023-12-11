package com.example.quizapp.feature.domain.model

import android.net.Uri

data class CreateQuizData(
    val image: Uri?,
    val title: String,
    val description: String
)
