package com.example.quizapp.feature.presentation.menu_graph.create_question

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CreateQuestionViewModel @Inject constructor(

) : ViewModel() {

    // Stan dla calej listy pytan
    private val _questionListState = mutableStateOf(CreateQuestionState())
    val questionListState: State<CreateQuestionState> = _questionListState

    // Stan dla biezacego pytania
    private val _currentQuestionState = mutableStateOf(CreateQuestionItem())
    val currentQuestionState: State<CreateQuestionItem> = _currentQuestionState


    fun onEvent(event: CreateQuestionEvent) {
        when (event) {
            is CreateQuestionEvent.AddAnswer -> {
                _currentQuestionState.value = _currentQuestionState.value.copy().apply {
                    answers[event.index] = event.value
                }
                copyQuestionToList()
            }

            is CreateQuestionEvent.AddImage -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    image = event.uri
                )
                copyQuestionToList()
            }
            is CreateQuestionEvent.CheckCorrectAnswer -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    correctAnswerIndex = event.index
                )
                copyQuestionToList()
            }
            is CreateQuestionEvent.CheckTime -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    selectedTime = event.time
                )
                copyQuestionToList()
            }
            is CreateQuestionEvent.EnteredAddQuestion -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    addQuestion = event.value
                )
                copyQuestionToList()
            }
            is CreateQuestionEvent.SelectedQuestion -> {
                _currentQuestionState.value = _questionListState.value
                    .createQuestionStateList[event.index].copy()
                _questionListState.value = _questionListState.value
                    .copy(currentQuestion = event.index)
            }
        }
    }
    private fun copyQuestionToList (){
        _questionListState.value.createQuestionStateList[_questionListState
            .value.currentQuestion] = _currentQuestionState.value.copy()
    }
}