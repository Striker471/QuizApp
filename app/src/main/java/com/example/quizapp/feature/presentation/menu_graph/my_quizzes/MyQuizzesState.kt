package com.example.quizapp.feature.presentation.menu_graph.my_quizzes

import com.example.quizapp.feature.domain.model.QuizData

data class MyQuizzesState (
    val listOfQuizzes: List<QuizData> = emptyList(),
    val searchText: String = ""
)
