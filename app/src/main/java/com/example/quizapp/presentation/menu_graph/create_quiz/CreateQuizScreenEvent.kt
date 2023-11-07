package com.example.quizapp.presentation.menu_graph.create_quiz

import android.net.Uri
import com.example.quizapp.presentation.auth_graph.login.LoginEvent

sealed class CreateQuizScreenEvent {
    data class AddImage(val uri: Uri?) :CreateQuizScreenEvent()
    data class EnteredTitle(val value: String) : CreateQuizScreenEvent()
    data class EnteredDescription(val value: String) : CreateQuizScreenEvent()
}