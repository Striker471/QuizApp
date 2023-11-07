package com.example.quizapp.presentation.menu_graph.my_quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.presentation.bottom_bar.BottomBar
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.QuizCardMyProfile
import com.example.quizapp.presentation.util.Screen

@Composable
fun MyQuizzesScreen(
    navController: NavController,
    viewModel: MyQuizzesViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsState()
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
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                )
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = searchText,
                onValueChange = { viewModel.onSearchTextChange(it) }
            )
            Text(
                "MyQuizzesScreen",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            QuizCardMyProfile()

        }
    }
}