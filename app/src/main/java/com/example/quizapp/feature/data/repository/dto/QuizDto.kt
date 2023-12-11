package com.example.quizapp.feature.data.repository.dto

import com.google.firebase.Timestamp

data class QuizDto(
    val authorId: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String? = null,
    val questionCount: Int = 0,
    val createdAt: Timestamp? = null,
    val views: Int = 0,
    val quizId: String = "",
    val userName: String = ""
)