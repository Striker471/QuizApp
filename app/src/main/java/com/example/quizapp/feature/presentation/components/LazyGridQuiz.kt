package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.quizapp.feature.domain.model.QuizItem

@Composable
fun LazyPagingGridQuiz (
    topBarText: String,
    quizzes: LazyPagingItems<QuizItem>,
    navController : NavController,
    onCardClick: (String) -> Unit = { }
){

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = quizzes.loadState) {
        if (quizzes.loadState.refresh is LoadState.Error) {
            snackbarHostState.showSnackbar(
                "Error: " + (quizzes.loadState.refresh as LoadState.Error).error.message
            )
        }
    }

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = topBarText,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            if (quizzes.loadState.refresh is LoadState.Loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .weight(1f)
                ) {
                    items(quizzes.itemCount) { index ->
                        quizzes[index]?.let { quiz ->
                            QuizCard(
                                title = quiz.title,
                                imageUrl = quiz.imageUrl,
                                userName = quiz.userName,
                                views = quiz.views,
                                onClick = {
                                }
                            )
                        }
                    }
                }
                if (quizzes.loadState.append is LoadState.Loading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}