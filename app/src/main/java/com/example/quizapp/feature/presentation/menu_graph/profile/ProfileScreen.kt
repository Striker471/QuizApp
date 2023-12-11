package com.example.quizapp.feature.presentation.menu_graph.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.bottom_bar.BottomBar
import com.example.quizapp.feature.presentation.components.CenterTopAppBar


@Composable
fun ProfileScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.quizmania),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
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
            Text(
                "ProfileScreen",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}