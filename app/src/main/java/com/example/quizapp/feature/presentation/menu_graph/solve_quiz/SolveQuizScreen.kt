package com.example.quizapp.feature.presentation.menu_graph.solve_quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.components.AnswerButton
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.components.AnswerState
import com.example.quizapp.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest


@ExperimentalMaterial3Api
@Composable
fun SolveQuizScreen(
    navController: NavController,
    viewModel: SolveQuizViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val timer by viewModel.timer.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is SolveQuizViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }

                is SolveQuizViewModel.UiEvent.NavigateToSubmit -> {
                    navController.navigate(Screen.SubmitQuizScreen.route


//                            navController.navigate(
//                            Screen.AddEditNoteScreen.route +
//                                    "?noteId=${note.id}&noteColor=${note.color}"
//                            )

                    ) {
                        popUpTo(Screen.MenuScreen.route){
                        }
                    }
                }

                SolveQuizViewModel.UiEvent.MenuNavigate ->
                    navController.navigate(Screen.MenuScreen.route){
                        popUpTo(Screen.MenuScreen.route)
                    }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.topBar)
                ),
                title = {
                    Box(
                        Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "${state.currentQuestion + 1}/${state.questionList.size}",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Text(
                            text = stringResource(R.string.solve_quiz),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (state.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else {

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewModel.secondTimer.toString(),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = timer,
                    color = when {
                        timer > 0.5f -> colorResource(R.color.green)
                        timer > 0.2f -> colorResource(R.color.yellow)
                        else -> colorResource(R.color.red)
                    },
                )
                Spacer(modifier = Modifier.height(32.dp))
                if (state.questionList[state.currentQuestion].imageUrl != null)
                    AsyncImage(
                        model = state.questionList[state.currentQuestion].imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1.5f)
                            .fillMaxWidth(),
                    )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.questionList[state.currentQuestion].questionDescription,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                for (i in 0..3) {
                    AnswerButton(
                        index = i,
                        text = state.questionList[state.currentQuestion].answers[i],
                        onClick = {

                            if (state.areButtonsEnabled)
                                viewModel.onEvent(SolveQuizEvent.SelectAnswer(i))
                        },
                        answerState =
                        if (viewModel.secondTimer > 0)
                            when {
                                state.selectedAnswer < 0 -> AnswerState.Unselected
                                state.selectedAnswer == i &&
                                        i == state.questionList[state.currentQuestion]
                                    .correctAnswerIndex -> AnswerState.Correct

                                i == state.selectedAnswer -> AnswerState.Incorrect
                                i == state.questionList[state.currentQuestion]
                                    .correctAnswerIndex -> AnswerState.Correct

                                else -> AnswerState.Unselected
                            } else
                            when (i) {
                                state.questionList[state.currentQuestion]
                                    .correctAnswerIndex -> AnswerState.Incorrect
                                else -> AnswerState.Unselected
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}