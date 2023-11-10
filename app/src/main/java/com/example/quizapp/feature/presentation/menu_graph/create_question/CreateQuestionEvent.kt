package com.example.quizapp.feature.presentation.menu_graph.create_question

import android.net.Uri

sealed class CreateQuestionEvent {
    data class EnteredQuestionDescription(val value: String) : CreateQuestionEvent()
    data class AddImage(val uri: Uri?) : CreateQuestionEvent()
    data class AddAnswer(val index: Int, val value: String) : CreateQuestionEvent()
    data class CheckCorrectAnswer(val index: Int) : CreateQuestionEvent()
    data class CheckTime(val time: Int) : CreateQuestionEvent()
    data class SelectedQuestion(val index: Int) : CreateQuestionEvent()
    object OnAddedQuestion : CreateQuestionEvent()
    object OnUpdateQuestion: CreateQuestionEvent()
}