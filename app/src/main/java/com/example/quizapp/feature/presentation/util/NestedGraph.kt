package com.example.quizapp.feature.presentation.util

sealed class NestedGraph(val route: String) {
    object AuthGraph : NestedGraph("auth_graph"){
        val startDestination = Screen.StartScreen.route
    }
    object MenuGraph :NestedGraph("menu_graph"){
        val startDestination = Screen.MenuScreen.route
    }

}
