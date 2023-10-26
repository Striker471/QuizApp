package com.example.quizapp.presentation.reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quizapp.R
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.MainActionButton
import com.example.quizapp.presentation.components.MainOutlinedTextField

@Composable
fun ResetPasswordScreen(

) {
    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.reset_password),
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
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
                )
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.reset_password_label),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.grey_text),
                modifier = Modifier
                    .widthIn(max = 300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                text = "",
                onValueChange = {},
                label = stringResource(R.string.email)
            )
            Spacer(modifier = Modifier.weight(1f))
            MainActionButton(
                onClick = {},
                text = stringResource(R.string.reset_password),
                modifier = Modifier
                    .padding(bottom = 32.dp)
            )
        }
    }
}