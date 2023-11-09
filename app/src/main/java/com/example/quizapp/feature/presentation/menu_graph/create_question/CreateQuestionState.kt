package com.example.quizapp.feature.presentation.menu_graph.create_question

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateQuestionItem(
    val image: Uri? = null,
    val addQuestion: String = "",
    val answers: SnapshotStateList<String> = mutableStateListOf("", "", "", ""),
    val correctAnswerIndex: Int = -1,
    val numberOfQuestion: Int = 0,
    val selectedTime: Int = 5
)

data class CreateQuestionState(
    val createQuestionStateList:
    MutableList<CreateQuestionItem> = mutableStateListOf(CreateQuestionItem()),
    val currentQuestion: Int = 1,
    val amountQuestions: Int = 1
)
