package com.example.quizapp.feature.domain.model

import com.google.firebase.Timestamp

data class Quiz(
    val authorId: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val questionCount: Int,
    val createdAt: Any,
    val views: Int
)