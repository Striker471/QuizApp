package com.example.quizapp.feature.presentation.menu_graph.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.feature.data.repository.impl.AuthRepository
import com.example.quizapp.feature.domain.use_case.menu.GetMenuScreenInitData
import com.example.quizapp.feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn

import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getMenuScreenInitData: GetMenuScreenInitData
) : ViewModel() {

    private val _state = mutableStateOf(MenuScreenState())
    val state: State<MenuScreenState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getMenuScreenInitData().onEach {
            when (it) {


                is Resource.Success -> {
                    _state.value = it.data
                }

                is Resource.Error -> {
                    _eventFlow.emit((UiEvent.ShowSnackbar(it.message)))
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logOut() {
        authRepository.logOut()
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}