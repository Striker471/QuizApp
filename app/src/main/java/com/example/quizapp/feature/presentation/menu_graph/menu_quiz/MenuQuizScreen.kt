package com.example.quizapp.feature.presentation.menu_graph.menu_quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.components.CenterTopAppBar
import com.example.quizapp.feature.presentation.components.MainActionButton
import com.example.quizapp.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MenuQuizScreen(
    navController: NavController,
    viewModel: MenuQuizViewModel = hiltViewModel()
) {
    val state by viewModel.state

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is MenuQuizViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }


    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.solve_quiz),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.MenuScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    bottom = innerPadding.calculateBottomPadding(),
                    top = innerPadding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = state.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 24.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(8.dp))

                state.imageUrl?.let {
                    AsyncImage(
                        model = state.imageUrl, contentDescription = null
                    )
                } ?: Image(
                    painterResource(R.drawable.image_dog),
                    contentDescription = null,
                    Modifier.size(320.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Creator: ${state.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 5
                )
                Spacer(modifier = Modifier.weight(1f))
                MainActionButton(
                    onClick = { },
                    text = stringResource(R.string.solve)
                )
                Spacer(modifier = Modifier.height(32.dp))

            }
        }
    }
}