package com.example.quizapp.feature.domain.model

import com.google.firebase.Timestamp

data class QuizData(
    val authorId: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val questionCount: Int,
    val createdAt: Timestamp? = null,
    val views: Int,
    val quizId: String,
    val userName: String
)
