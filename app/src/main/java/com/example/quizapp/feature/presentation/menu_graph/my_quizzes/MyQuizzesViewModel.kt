package com.example.quizapp.feature.presentation.menu_graph.my_quizzes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.domain.model.QuizData
import com.example.quizapp.feature.domain.use_case.menu.GetMyQuizzes
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyQuizzesViewModel @Inject constructor(
    private val getMyQuizzes: GetMyQuizzes
) : ViewModel() {


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var unfilteredList by mutableStateOf(emptyList<QuizData>())
    var state by mutableStateOf(MyQuizzesState())
        private set


    init {
        getMyQuizzes().onEach {
            when (it) {
                is Resource.Error -> _eventFlow.emit(UiEvent.ShowSnackbar(it.message))
                Resource.Loading -> {}
                is Resource.Success -> {
                    state = state.copy(
                        listOfQuizzes = it.data
                    )
                    unfilteredList = it.data
                }
            }
        }.launchIn(viewModelScope)
    }


//    private val _persons = MutableStateFlow(allPersons)
//    val persons = searchText
//        .debounce(1000L)
//        .onEach { _isSearching.update { true } }
//        .combine(_persons) { text, persons ->
//            if(text.isBlank()) {
//                persons
//            } else {
//                delay(2000L)
//                persons.filter {
//                    it.doesMatchSearchQuery(text)
//                }
//            }
//        }
//        .onEach { _isSearching.update { false } }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            _persons.value
//        )


    fun onSearchTextChange(text: String) {
        state = state.copy(
            searchText = text
        )
        filterList()
    }

    private fun filterList() {
        state = if (state.searchText.isBlank()) {
            state.copy(
                listOfQuizzes = unfilteredList
            )
        } else {
            state.copy(
                listOfQuizzes = unfilteredList.filter {
                    it.title.contains(state.searchText, ignoreCase = true)
                }
            )
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}