package com.example.quizapp.feature.presentation.menu_graph.create_question

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.use_case.menu.AddQuestion
import com.example.quizapp.feature.domain.use_case.menu.UpdateQuestion
import com.example.quizapp.feature.domain.util.Resource
import com.example.quizapp.feature.presentation.menu_graph.create_quiz.CreateQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class CreateQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addQuestion: AddQuestion,
    private val updateQuestion: UpdateQuestion
) : ViewModel() {

    // Stan dla calej listy pytan
    private val _questionListState = mutableStateOf(CreateQuestionState())
    val questionListState: State<CreateQuestionState> = _questionListState

    // Stan dla biezacego pytania
    private val _currentQuestionState = mutableStateOf(CreateQuestionItem())
    val currentQuestionState: State<CreateQuestionItem> = _currentQuestionState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var quizId: String = ""

    init {
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
                addQuestion(
                    _currentQuestionState.value,
                    quizId
                ).onEach {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _currentQuestionState.value = _currentQuestionState.value.copy(
                                imageUrl = it.data.imageUrl,
                                questionId = it.data.questionId,
                                image = null
                            )
                            _eventFlow.emit(UiEvent.ShowSnackbar("jest git"))
                            copyLastQuestionToList()
                            val createQuestionItem = CreateQuestionItem()
                            _questionListState.value.createQuestionStateList.add(createQuestionItem)

                            _currentQuestionState.value = createQuestionItem
                            _questionListState.value = _questionListState.value.copy(
                                currentQuestion = _questionListState.value.currentQuestion + 1,
                                amountQuestions = _questionListState.value.amountQuestions + 1
                            )
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                        }
                    }
                }.launchIn(
                    viewModelScope
                )
            }

            is CreateQuestionEvent.OnUpdateQuestion -> {
                updateQuestion(
                    _questionListState.value.createQuestionStateList[
                        _questionListState.value.currentQuestion - 1],
                    _currentQuestionState.value,
                    quizId
                ).onEach {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _currentQuestionState.value = _currentQuestionState.value.copy(
                                imageUrl = it.data,
                                image = null
                            )
                            _eventFlow.emit(UiEvent.ShowSnackbar("jest git"))
                            updateQuestionToList()
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun copyLastQuestionToList() {
        _questionListState.value.createQuestionStateList[_questionListState
            .value.createQuestionStateList.lastIndex] =
            _currentQuestionState.value
    }

    private fun updateQuestionToList() {
        _questionListState.value.createQuestionStateList[
            _questionListState.value.currentQuestion - 1] = _currentQuestionState.value
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}