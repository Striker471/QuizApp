package com.example.quizapp.feature.presentation.menu_graph.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.quizapp.feature.domain.model.QuizItem
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    pager: Pager<DocumentSnapshot, QuizItem>
) : ViewModel() {

    val quizPagingFlow = pager
        .flow
        .cachedIn(viewModelScope)


}