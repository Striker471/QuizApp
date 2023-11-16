package com.example.quizapp.feature.presentation.menu_graph.menu

import com.example.quizapp.feature.domain.model.QuizItem

data class MenuScreenState(
    val theLatestList: List<QuizItem> = emptyList(),
    val mostPopularList: List<QuizItem> = emptyList(),
    val isLoading: Boolean = false
)


