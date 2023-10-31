package com.example.quizapp.presentation.create_quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.presentation.components.AddImageCard
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.MainActionButton

@Composable
fun CreateQuizScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.create_quiz)
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
            Spacer(modifier = Modifier.height(32.dp))
            AddImageCard()
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = stringResource(R.string.enter_quiz_title))
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = stringResource(R.string.description))
                    },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            MainActionButton(
                onClick = {},
                text = stringResource(R.string.add_question),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
            )
        }
    }
}