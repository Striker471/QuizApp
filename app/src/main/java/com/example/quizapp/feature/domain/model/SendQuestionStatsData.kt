package com.example.quizapp.feature.domain.model

data class SendQuestionStatsData(
    val quizId: String,
    val questionId: String,
    val isSelectCorrectQuestion: Boolean
)