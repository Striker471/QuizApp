package com.example.quizapp.presentation.start

import androidx.lifecycle.ViewModel
import com.example.quizapp.domain.use_case.auth.IsUserSignedIn
import com.example.quizapp.feature_repository.data.repository.AuthRepository
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