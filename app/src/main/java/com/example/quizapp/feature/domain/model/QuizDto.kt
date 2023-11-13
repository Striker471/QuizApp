package com.example.quizapp.feature.domain.model

data class QuizDto(
    val authorId: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val questionCount: Int,
    val createdAt: Any,
    val views: Int,
    val quizId: String
)