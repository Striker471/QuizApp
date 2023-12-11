package com.example.quizapp.feature.presentation.menu_graph.solve_quiz

sealed class SolveQuizEvent {
    data class SelectAnswer(val id: Int) : SolveQuizEvent()
}