package com.example.quizapp.feature.presentation.menu_graph.most_viewed_quizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.quizapp.feature.data.repository.Pagers
import com.example.quizapp.feature.domain.model.QuizItem
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MostViewedQuizzesViewModel @Inject constructor(
    @Named(Pagers.MOST_VIEWED_QUIZ_PAGER) private val pager: Pager<DocumentSnapshot, QuizItem>
) : ViewModel() {

    val quizPagingFlow = pager
        .flow
        .cachedIn(viewModelScope)

}