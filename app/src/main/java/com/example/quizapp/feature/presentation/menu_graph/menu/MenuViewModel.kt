package com.example.quizapp.feature.presentation.menu_graph.menu

import androidx.lifecycle.ViewModel
import com.example.quizapp.feature.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
 private val authRepository: AuthRepository
) : ViewModel() {


    fun logOut (){
        authRepository.logOut()
    }

}