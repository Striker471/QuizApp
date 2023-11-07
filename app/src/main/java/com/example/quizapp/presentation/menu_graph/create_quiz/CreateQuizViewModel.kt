package com.example.quizapp.presentation.menu_graph.create_quiz

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor() : ViewModel() {

    private val _image: MutableState<Uri?> = mutableStateOf(null)
    val image: State<Uri?> = _image

    fun onEvent(event: CreateQuizScreenEvent) {

        when (event) {
            is CreateQuizScreenEvent.AddImage -> {
                _image.value = event.uri
            }

            is CreateQuizScreenEvent.EnteredDescription -> TODO()
            is CreateQuizScreenEvent.EnteredTitle -> TODO()
        }
    }
}