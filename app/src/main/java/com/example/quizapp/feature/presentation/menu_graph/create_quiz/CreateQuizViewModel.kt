package com.example.quizapp.feature.presentation.menu_graph.create_quiz

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.use_case.menu.CreateQuiz
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor(
    private val createQuiz: CreateQuiz
) : ViewModel() {

    private val _state = mutableStateOf(CreateQuizState())
    val state: State<CreateQuizState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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

            is CreateQuizScreenEvent.CreateQuizOnClick -> {

                createQuiz(
                    CreateQuizData(
                        image = _image.value,
                        title = _state.value.title,
                        description = state.value.description
                    )
                ).onEach {
                    when (it) {
                        is Resource.Error -> {
                            _eventFlow.emit((UiEvent.ShowSnackbar(it.message)))
                        }

                        is Resource.Loading -> {
                        }

                        is Resource.Success -> {

                            _eventFlow.emit((UiEvent.CreateQuestionNavigate(it.data)))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    sealed class UiEvent() {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class CreateQuestionNavigate(val quizId: String) : UiEvent()
    }
}