package com.example.quizapp.feature.domain.model

import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.data.repository.dto.QuizDto

data class QuizWithQuestions(
    val quiz: QuizDto,
    val questions: List<QuestionDto>
)