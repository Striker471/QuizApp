package com.example.quizapp.feature.domain.model

data class QuizItem(
    val imageUrl: String? = null,
    val title: String,
    val userName: String,
    val views: Int = 0,
    val quizId: String
)