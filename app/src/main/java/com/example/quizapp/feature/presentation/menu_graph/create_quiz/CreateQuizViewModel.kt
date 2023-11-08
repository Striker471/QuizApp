package com.example.quizapp.feature.presentation.menu_graph.create_quiz

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(CreateQuizState())
    val state: State<CreateQuizState> = _state

    private val _image: MutableState<Uri?> = mutableStateOf(null)
    val image: State<Uri?> = _image

    fun onEvent(event: CreateQuizScreenEvent) {

        when (event) {
            is CreateQuizScreenEvent.AddImage -> {
                _image.value = event.uri
            }

            is CreateQuizScreenEvent.EnteredTitle -> {
                _state.value = _state.value.copy(title = event.value)
            }

            is CreateQuizScreenEvent.EnteredDescription -> {
                _state.value = _state.value.copy(description = event.value)
            }

            CreateQuizScreenEvent.CreateQuizOnClick -> {

            }
        }
    }
}