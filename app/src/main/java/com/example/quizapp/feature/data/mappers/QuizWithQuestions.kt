package com.example.quizapp.feature.data.mappers

import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.data.repository.dto.QuizDto

data class QuizWithQuestions(
    val quiz: QuizDto,
    val questions: List<QuestionDto>
)