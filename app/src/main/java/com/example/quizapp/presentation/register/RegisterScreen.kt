package com.example.quizapp.presentation.register

import android.graphics.drawable.Icon
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quizapp.R
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.MainActionButton
import com.example.quizapp.presentation.components.MainOutlinedTextField
import com.example.quizapp.presentation.components.OutlinedButtonWithImage

@Composable
fun RegisterScreen() {

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.create_an_account),
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
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = stringResource(R.string.create_account_body),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.grey_text),
                modifier = Modifier
                    .widthIn(max = 300.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            MainOutlinedTextField(
                "siemanko",
                {},
                stringResource(R.string.username)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                "siemanko",
                {},
                stringResource(R.string.email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                "siemanko",
                {},
                stringResource(R.string.password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                "",
                {},
                stringResource(R.string.repeat_password)
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )

            MainActionButton(
                onClick = {},
                text = stringResource(R.string.sign_up),
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}