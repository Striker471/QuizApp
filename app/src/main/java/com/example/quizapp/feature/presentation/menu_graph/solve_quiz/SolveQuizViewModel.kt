package com.example.quizapp.feature.presentation.menu_graph.solve_quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.model.SendQuestionStatsData
import com.example.quizapp.feature.domain.use_case.menu.FinishSolvingQuiz
import com.example.quizapp.feature.domain.use_case.menu.GetQuestionsToSolve
import com.example.quizapp.feature.domain.use_case.menu.SendStatsOfQuestionToDb
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolveQuizViewModel @Inject constructor(
    private val getQuestionsToSolve: GetQuestionsToSolve,
    private val sendStatsOfQuestionToDb: SendStatsOfQuestionToDb,
    private val finishSolvingQuiz: FinishSolvingQuiz,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(SolveQuizState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var timer = MutableStateFlow(1f)
        private set
    var secondTimer by mutableStateOf(20)
        private set

    private var quizId: String = ""

    private var timerJob: Job? = null

    init {
        savedStateHandle.get<String>("quizId")?.let {
            quizId = it
            getQuestionsToSolve(it).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnackbar(result.message))
                        delay(3000)
                        _eventFlow.emit(UiEvent.MenuNavigate)
                    }
                    is Resource.Loading -> state = state.copy(
                        isLoading = true
                    )
                    is Resource.Success -> {
                        state = result.data.copy(
                            isLoading = false
                        )
                        startTimer()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        secondTimer = getCurrentQuestion().selectedTime
        var iterations = 0

        timerJob = viewModelScope.launch {
            while (timer.value > 0) {
                delay(10)
                iterations++
                if (iterations % 100 == 0)
                    secondTimer--

                timer.value -= 0.01f / getCurrentQuestion().selectedTime
            }
            processSelectedAnswer(-1)
        }
    }

    fun onEvent(event: SolveQuizEvent) {
        when (event) {
            is SolveQuizEvent.SelectAnswer -> {
                timerJob?.cancel()
                processSelectedAnswer(event.id)
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class NavigateToSubmit(val score: Int) : UiEvent()
        object MenuNavigate : UiEvent()
    }

    private fun getCurrentQuestion(): SolveQuizQuestionData {
        return state.questionList[state.currentQuestion]
    }

    private fun processSelectedAnswer(answerId: Int) {
        state = state.copy(
            selectedAnswer = answerId,
            areButtonsEnabled = false
        )
        val question = getCurrentQuestion()
        sendStatsOfQuestionToDb(
            SendQuestionStatsData(
                quizId = quizId,
                questionId = question.questionId,
                isSelectCorrectQuestion = answerId == question.correctAnswerIndex
            )
        ).onEach { resource ->
            when (resource) {
                is Resource.Error -> _eventFlow.emit(UiEvent.ShowSnackbar(resource.message))
                is Resource.Loading -> {}
                is Resource.Success -> handleAnswerSelectedSuccess()
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun handleAnswerSelectedSuccess() {
        state = state.copy(
            score = state.score + getScoreFromSelectedQuestion()
        )
        delay(3000)
        val isLastQuestion = state.currentQuestion == state.questionList.size - 1
        if (isLastQuestion) {
            finishQuiz()
        } else {
            moveToNextQuestion()
        }
    }

    private fun moveToNextQuestion() {
        val nextQuestionIndex = state.currentQuestion + 1
        state = state.copy(
            currentQuestion = nextQuestionIndex,
            selectedAnswer = -1,
            areButtonsEnabled = true
        )
        timer.value = 1f
        startTimer()
    }

    private fun finishQuiz() {
        finishSolvingQuiz(quizId, state.score).onEach {
            when (it) {
                is Resource.Error -> _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                Resource.Loading -> {}
                is Resource.Success -> _eventFlow.emit(UiEvent.NavigateToSubmit(state.score))
            }
        }.launchIn(viewModelScope)
    }

    private fun getScoreFromSelectedQuestion(): Int {
        if (state.selectedAnswer == getCurrentQuestion().correctAnswerIndex) {
            return 20 + (timer.value * 20).toInt()
        }
        return 0
    }
}