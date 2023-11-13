package com.example.quizapp.feature.presentation.menu_graph.menu

data class MenuScreenState(
    val theLatestList: List<ItemScreenData>,
    val mostPopularList: List<ItemScreenData>
)



data class ItemScreenData(
    val imageUrl: String? = null,
    val title: String,
    val userName: String,
    val quizId: String
)
