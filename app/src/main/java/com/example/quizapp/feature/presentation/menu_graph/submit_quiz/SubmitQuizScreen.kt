package com.example.quizapp.feature.presentation.menu_graph.submit_quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.components.MainActionButton
import com.example.quizapp.feature.presentation.menu_graph.create_quiz.CreateQuizScreenEvent
import com.example.quizapp.feature.presentation.util.Screen

@ExperimentalMaterial3Api
@Composable
fun SubmitQuizScreen(
    navController: NavController,
    score: Int
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.topBar)
                ),
                title = {
                    Text(text = stringResource(R.string.score))
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                stringResource(R.string.score) + ": $score" + stringResource(R.string.points)
            )
            Spacer(modifier = Modifier.weight(1f))
            MainActionButton(
                onClick = {
                    navController.navigate(Screen.MenuScreen.route) {
                        popUpTo(Screen.MenuScreen.route)
                    }
                },
                text = stringResource(R.string.finish),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}