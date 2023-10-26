package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.presentation.components.CenterTopAppBar
import com.example.quizapp.presentation.components.MainActionButton
import com.example.quizapp.presentation.components.MainOutlinedTextField
import com.example.quizapp.presentation.components.OutlinedButtonWithImage
import com.example.quizapp.presentation.login.LoginScreen
import com.example.quizapp.presentation.register.RegisterScreen
import com.example.quizapp.presentation.reset_password.ResetPasswordScreen
import com.example.quizapp.presentation.start.StartScreen
import com.example.quizapp.ui.theme.QuizAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                var text by remember { mutableStateOf("") }
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = colorResource(R.color.surface)) {
//                    RegisterScreen()
                    StartScreen()
//                    LoginScreen()
//                    ResetPasswordScreen()
//                    Scaffold(
//                        topBar = {
//                            CenterTopAppBar(
//                                titleText = "Siema",
//                                navigationIcon = {
//                                    IconButton(onClick = { /* do something */ }) {
//                                        Icon(
//                                            imageVector = Icons.Filled.ArrowBack,
//                                            contentDescription = "Localized description"
//                                        )
//                                    }
//                                },
//                            )
//                        }) { innerPadding ->
//                        Column(
//                            modifier = Modifier
//                                .padding(
//                                    bottom = innerPadding.calculateBottomPadding(),
//                                    top = innerPadding.calculateTopPadding()
//                                )
//                                .fillMaxSize(),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            MainActionButton({}, "Sign up")
//                            Spacer(Modifier.height(16.dp))
//                            MainOutlinedTextField(
//                                text = text,
//                                onValueChange = { text = it},
//                                label = "siema"
//                            )
//                            MainOutlinedTextField(
//                                text = text,
//                                onValueChange = { text = it},
//                                label = "siema"
//                            )
//                            Spacer(Modifier.height(16.dp))
//
//                            Icon(
//                                painter = painterResource(R.drawable.google_icon),
//                                contentDescription = null,
//                                modifier = Modifier.size(100.dp),
//                                tint = Color.Unspecified
//                            )
//                            Spacer(Modifier.height(16.dp))
//                            OutlinedButtonWithImage()
//                        }
//                    }
                }
            }
        }
    }
}


//actions = {
//    IconButton(onClick = { /* do something */ }) {
//        Icon(
//            imageVector = Icons.Filled.Menu,
//            contentDescription = "Localized description"
//        )
//    }
//},

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuizAppTheme {
        Greeting("Android")
    }
}