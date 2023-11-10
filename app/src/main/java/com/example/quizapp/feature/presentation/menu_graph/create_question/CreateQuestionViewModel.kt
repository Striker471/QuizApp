package com.example.quizapp.feature.presentation.menu_graph.create_question

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CreateQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Stan dla calej listy pytan
    private val _questionListState = mutableStateOf(CreateQuestionState())
    val questionListState: State<CreateQuestionState> = _questionListState

    // Stan dla biezacego pytania
    private val _currentQuestionState = mutableStateOf(CreateQuestionItem())
    val currentQuestionState: State<CreateQuestionItem> = _currentQuestionState

    private var quizId : String = ""

    init{
        savedStateHandle.get<String>("quizId")?.let {
            quizId = it
        }
    }

    fun onEvent(event: CreateQuestionEvent) {
        when (event) {
            is CreateQuestionEvent.AddAnswer -> {
                _currentQuestionState.value = _currentQuestionState.value.copy().apply {
                    answers[event.index] = event.value
                }
            }

            is CreateQuestionEvent.AddImage -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    image = event.uri
                )
            }

            is CreateQuestionEvent.CheckCorrectAnswer -> {
                if (_currentQuestionState.value.correctAnswerIndex == event.index) {
                    _currentQuestionState.value = _currentQuestionState.value.copy(
                        correctAnswerIndex = -1
                    )
                } else {
                    _currentQuestionState.value = _currentQuestionState.value.copy(
                        correctAnswerIndex = event.index
                    )
                }
            }

            is CreateQuestionEvent.CheckTime -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    selectedTime = event.time
                )
            }

            is CreateQuestionEvent.EnteredQuestionDescription -> {
                _currentQuestionState.value = _currentQuestionState.value.copy(
                    questionDescription = event.value
                )
            }

            is CreateQuestionEvent.SelectedQuestion -> {
                if (_questionListState.value.isLastQuestion()) {
                    copyLastQuestionToList()
                }
                _currentQuestionState.value = _questionListState.value
                    .createQuestionStateList[event.index - 1].copyWithNewAnswers()
                _questionListState.value = _questionListState.value
                    .copy(currentQuestion = event.index)
            }

            CreateQuestionEvent.OnAddedQuestion -> {
                //repository
                copyLastQuestionToList()
//trza zaktualizowac     val questionId: String? = null
                val createQuestionItem = CreateQuestionItem()
                _questionListState.value.createQuestionStateList.add(createQuestionItem)

                _currentQuestionState.value = createQuestionItem
                _questionListState.value = _questionListState.value.copy(
                    currentQuestion = _questionListState.value.currentQuestion + 1,
                    amountQuestions = _questionListState.value.amountQuestions + 1
                )
            }

            is CreateQuestionEvent.OnUpdateQuestion -> {


            }
        }
    }

    private fun copyLastQuestionToList() {
        _questionListState.value.createQuestionStateList[_questionListState
            .value.createQuestionStateList.lastIndex] =
            _currentQuestionState.value
    }
}