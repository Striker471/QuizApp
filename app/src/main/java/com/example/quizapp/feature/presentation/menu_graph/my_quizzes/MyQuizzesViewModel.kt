package com.example.quizapp.feature.presentation.menu_graph.my_quizzes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MyQuizzesViewModel @Inject constructor(

) :ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

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
        _searchText.value = text
    }

}