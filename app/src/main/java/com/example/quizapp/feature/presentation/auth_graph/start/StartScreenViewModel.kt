package com.example.quizapp.feature.presentation.auth_graph.start

import androidx.lifecycle.ViewModel
import com.example.quizapp.feature.domain.use_case.auth.IsUserSignedIn
import com.example.quizapp.feature.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel
@Inject constructor(
    private val isUserSignedIn: IsUserSignedIn
) : ViewModel() {

    fun checkIfUserIsSignedIn() : Boolean{
        return isUserSignedIn()
    }

}