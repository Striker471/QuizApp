package com.example.quizapp.feature.data.mappers

import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.domain.model.QuizData
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

fun QuizDto.toQuizData() : QuizData{
    return QuizData(
        authorId = authorId,
        title = title,
        description = description,
        imageUrl = imageUrl,
        questionCount = questionCount,
        createdAt = createdAt,
        views = views,
        quizId = quizId,
        userName = userName
    )
}