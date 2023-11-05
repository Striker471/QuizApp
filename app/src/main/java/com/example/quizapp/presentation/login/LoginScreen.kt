package com.example.quizapp.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.MainActionButton
import com.example.quizapp.presentation.components.MainOutlinedTextField
import com.example.quizapp.presentation.components.OutlinedButtonWithImage
import com.example.quizapp.presentation.util.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.login),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
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
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.hello_there),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButtonWithImage(
                    onClick = { viewModel.onEvent(LoginEvent.GoogleSignIn) },
                    text = stringResource(R.string.continue_with_google),
                    icon = R.drawable.google_icon
                )
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButtonWithImage(
                    onClick = { viewModel.onEvent(LoginEvent.FacebookSignIn) },
                    text = stringResource(R.string.continue_with_facebook),
                    icon = R.drawable.facebook_icon
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.or))
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                text = state.email,
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                label = stringResource(R.string.email)

            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                text = state.password,
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                label = stringResource(R.string.password)
            )
            Spacer(modifier = Modifier.height(32.dp))
            val checkedState = remember { mutableStateOf(true) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
                Text(
                    text = stringResource(R.string.rembember_me),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.forgot_password),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.rubik_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = colorResource(R.color.purple_button),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.ResetPasswordScreen.route)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            MainActionButton(
                onClick = { viewModel.onEvent(LoginEvent.SignIn) },
                text = stringResource(R.string.sign_in),
                modifier = Modifier.padding(bottom = 32.dp)
            )

        }
    }
}