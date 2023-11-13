package com.example.quizapp.feature.presentation.menu_graph.menu

sealed class MenuScreenEvent {
    object OnTheLatestClick : MenuScreenEvent()
    object OnMostPopularClick : MenuScreenEvent()

}