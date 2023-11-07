package com.example.quizapp.presentation.menu_graph.create_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.presentation.components.CenterTopAppBar
import kotlinx.coroutines.launch


@Composable
fun CreateQuestionScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.create_quiz),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var listState = remember { mutableStateOf((1..30).toList()) }
            val list: MutableList<Int> = listState.value.toMutableList()
            var selectedItem by remember { mutableStateOf(1) }
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(listState.value) { index, item ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                color = if (selectedItem == index) Color.Black else Color.Transparent
                            )
                            .clickable {
                                selectedItem = index
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(if (index - 4 < 0) 0 else index - 4)
                                }
                            }

                    ) {
                        Text(
                            text = item.toString(),
                            color = if (selectedItem == index) Color.White else Color.Black
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .clickable {
                                list.add(list.size + 1)
                                listState.value = list
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(list.size - 1)
                                    selectedItem = list.size - 1
                                }
                            }
                            .size(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "+")
                    }
                }
            }
        }
    }
}
