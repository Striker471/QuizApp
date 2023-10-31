package com.example.quizapp.presentation.menu

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.MenuCardWithNavigation
import com.example.quizapp.presentation.components.QuizCard

@Composable
fun MenuScreen(
    navController: NavController
) {


    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = "Siema",
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    bottom = innerPadding.calculateBottomPadding(),
                    top = innerPadding.calculateTopPadding()
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            MenuCardWithNavigation(
                "The latest"
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                items(8) {
                    QuizCard()
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}