package com.example.quizapp.feature.data.mappers

import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.domain.model.QuestionData

fun QuestionDto.toQuestionData(): QuestionData {
    return QuestionData(
        imageUrl = imageUrl,
        questionDescription = questionDescription,
        answers = answers,
        correctAnswerIndex = correctAnswerIndex,
        selectedTime = selectedTime,
        questionId = questionId,
        attempts = attempts,
        correctAttempts = correctAttempts
    )
}