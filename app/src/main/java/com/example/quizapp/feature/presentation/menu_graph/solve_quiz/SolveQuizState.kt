package com.example.quizapp.feature.presentation.menu_graph.solve_quiz

import com.example.quizapp.feature.domain.model.QuizData

data class SolveQuizState(
    val currentQuestion: Int = 0,
    val quizData: QuizData? = null,
    val questionList: List<SolveQuizQuestionData> = emptyList(),
    val isLoading: Boolean = false,
    val selectedAnswer : Int = -1,
    val areButtonsEnabled: Boolean = true,
    val score: Int = 0
)


data class SolveQuizQuestionData(
    val imageUrl: String? = null,
    val questionDescription: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswerIndex: Int = -1,
    val selectedTime: Int = 20,
    val questionId: String = "",
)