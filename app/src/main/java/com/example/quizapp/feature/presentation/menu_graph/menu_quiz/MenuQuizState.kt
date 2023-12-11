package com.example.quizapp.feature.presentation.menu_graph.menu_quiz

data class MenuQuizState(
    val title: String = "",
    val imageUrl: String? = null,
    val author: String = "",
    val description: String = "",
    val quizId: String = "",
    val views: Int = 0,
    val isLoading: Boolean = false
)