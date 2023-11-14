package com.example.quizapp.feature.presentation.menu_graph.menu

data class MenuScreenState(
    val theLatestList: List<ItemScreenData> = emptyList(),
    val mostPopularList: List<ItemScreenData> = emptyList(),
    val isLoading: Boolean = false
)

data class ItemScreenData(
    val imageUrl: String? = null,
    val title: String,
    val userName: String,
    val views: Int = 0,
    val quizId: String
)
