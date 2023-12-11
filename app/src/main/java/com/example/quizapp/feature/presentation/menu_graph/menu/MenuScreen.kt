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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.quizapp.feature.presentation.menu_graph.menu.components.LogoutDropdownMenu
import com.example.quizapp.feature.presentation.util.NestedGraph
import com.example.quizapp.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val state by viewModel.state
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is MenuViewModel.UiEvent.Logout -> {
                    navController.navigate(NestedGraph.AuthGraph.route) {
                        popUpTo(NestedGraph.MenuGraph.route) {
                            inclusive = true
                        }
                    }
                }

                is MenuViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.quizmania),
                navigationIcon = {
                    IconButton(onClick = {
                        isDropdownExpanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                        LogoutDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismiss = { isDropdownExpanded = false },
                            onLogoutCLick = { viewModel.logOut() }
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
                    onClick = { navController.navigate(Screen.TheLatestQuizzesScreen.route) }
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
                    onClick = { navController.navigate(Screen.MostViewedQuizzesScreen.route) }
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