package com.example.quizapp.feature.data.mappers

import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.domain.model.QuizItem


fun QuizDto.toQuizItem(): QuizItem{
    return QuizItem(
        imageUrl = this.imageUrl,
        title = this.title,
        userName = this.userName,
        views = this.views,
        quizId = this.quizId
    )
}