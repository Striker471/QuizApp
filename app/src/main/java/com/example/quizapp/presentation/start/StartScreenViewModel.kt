package com.example.quizapp.presentation.start

import androidx.lifecycle.ViewModel
import com.example.quizapp.domain.use_case.auth.IsUserSignedIn
import com.example.quizapp.feature_repository.data.repository.AuthRepository
import javax.inject.Inject

class StartScreenViewModel
@Inject constructor(
    private val checkIfUserIsUserSignedIn: IsUserSignedIn
) : ViewModel() {

    fun isUserSignedIn() : Boolean{
        return checkIfUserIsUserSignedIn()
    }

}