package com.example.quizapp.feature.presentation.menu_graph.my_quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.bottom_bar.BottomBar
import com.example.quizapp.feature.presentation.components.CenterTopAppBar
import com.example.quizapp.feature.presentation.menu_graph.my_quizzes.components.QuizCardMyProfile
import com.example.quizapp.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyQuizzesScreen(
    navController: NavController,
    viewModel: MyQuizzesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is MyQuizzesViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(it.message)
            }
        }
    }


    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.my_quizzes),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.CreateQuizScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.searchText,
                onValueChange = { viewModel.onSearchTextChange(it) },
                placeholder = { Text(stringResource(R.string.filter)) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (state.listOfQuizzes.isEmpty())
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.do_not_have_quizzes))
                } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.listOfQuizzes) {
                        QuizCardMyProfile(
                            title = it.title,
                            quizUrl = it.imageUrl,
                            views = it.views,
                            onClick = {
                                navController.navigate(
                                    Screen.CreateQuestionScreen.route +
                                            "?quizId=${it.quizId}"
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}