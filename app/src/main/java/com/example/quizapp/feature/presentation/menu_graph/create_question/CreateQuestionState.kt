package com.example.quizapp.feature.presentation.menu_graph.create_question

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

data class CreateQuestionItem(
    val image: Uri? = null,
    val imageUrl: String? = null ,
    val questionDescription: String = "",
    val answers: SnapshotStateList<String> = mutableStateListOf("", "", "", ""),
    val correctAnswerIndex: Int = -1,
    val selectedTime: Int = 5,
    val questionId: String? = null
){
    fun copyWithNewAnswers(): CreateQuestionItem {
        return this.copy(answers = answers.toList().toMutableStateList())
    }
}


data class CreateQuestionState(
    val createQuestionStateList:
    MutableList<CreateQuestionItem> = mutableStateListOf(CreateQuestionItem()),
    val currentQuestion: Int = 1,
){
    fun isLastQuestion() :Boolean{
        return currentQuestion == createQuestionStateList.size
    }
}
