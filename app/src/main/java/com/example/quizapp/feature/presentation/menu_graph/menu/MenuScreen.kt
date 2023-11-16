package com.example.quizapp.feature.presentation.menu_graph.menu


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import com.example.quizapp.R
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.feature.presentation.bottom_bar.BottomBar
import com.example.quizapp.feature.presentation.components.CenterTopAppBar
import com.example.quizapp.feature.presentation.components.MenuCardWithNavigation
import com.example.quizapp.feature.presentation.components.QuizCard
import com.example.quizapp.feature.presentation.util.Screen


@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val state by viewModel.state

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.quizmania),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.logOut()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }) { innerPadding ->

        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(
                        bottom = innerPadding.calculateBottomPadding(),
                        top = innerPadding.calculateTopPadding()
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                MenuCardWithNavigation(
                    title = stringResource(R.string.the_latest),
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    items(state.theLatestList) {
                        QuizCard(
                            title = it.title,
                            imageUrl = it.imageUrl,
                            userName = it.userName,
                            views = it.views,
                            onClick = {
                                navController.navigate(
                                    Screen.MenuQuizScreen.route +
                                            "?quizId=${it.quizId}"
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                MenuCardWithNavigation(
                    title = stringResource(R.string.most_popular),
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    items(state.mostPopularList) {
                        QuizCard(
                            title = it.title,
                            imageUrl = it.imageUrl,
                            userName = it.userName,
                            views = it.views,
                            onClick = {
                                navController.navigate(
                                    Screen.MenuQuizScreen.route +
                                            "?quizId=${it.quizId}"
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}