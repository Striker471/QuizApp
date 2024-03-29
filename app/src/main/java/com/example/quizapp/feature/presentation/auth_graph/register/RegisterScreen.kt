package com.example.quizapp.feature.presentation.auth_graph.register


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.components.CenterTopAppBar
import com.example.quizapp.feature.presentation.components.MainActionButton
import com.example.quizapp.feature.presentation.components.MainOutlinedTextField
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordRepeatVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is RegisterViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.create_an_account),
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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                text = state.userName,
                onValueChange = { viewModel.onEvent(RegisterEvent.EnteredUserName(it)) },
                label = stringResource(R.string.username)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                text = state.email,
                onValueChange = { viewModel.onEvent(RegisterEvent.EnteredEmail(it)) },
                label = stringResource(R.string.email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                text = state.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.EnteredPassword(it)) },
                label = stringResource(R.string.password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainOutlinedTextField(
                text = state.repeatPassword,
                onValueChange = { viewModel.onEvent(RegisterEvent.EnteredRepeatPassword(it)) },
                label = stringResource(R.string.repeat_password),
                visualTransformation = if (passwordRepeatVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordRepeatVisible = !passwordRepeatVisible }) {
                        Icon(
                            imageVector = if (passwordRepeatVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )

            MainActionButton(
                onClick = { viewModel.onEvent(RegisterEvent.SignUp) },
                text = stringResource(R.string.sign_up),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(0.83f),
                enabled = !state.isLoading
            )
        }
    }
}