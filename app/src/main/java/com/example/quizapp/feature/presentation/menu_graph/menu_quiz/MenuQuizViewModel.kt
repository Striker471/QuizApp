package com.example.quizapp.feature.presentation.menu_graph.menu_quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.use_case.menu.GetQuiz
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MenuQuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getQuiz: GetQuiz
) : ViewModel() {

    private val _state = mutableStateOf(MenuQuizState(
        isLoading = true
    ))
    val state: State<MenuQuizState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("quizId")?.let { quizId ->
            getQuiz(quizId).onEach {
                when (it) {
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = it.data
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    sealed class UiEvent{
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}