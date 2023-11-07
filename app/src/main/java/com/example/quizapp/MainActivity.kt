package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.create_quiz.CreateQuizScreen
import com.example.quizapp.presentation.library.LibraryScreen
import com.example.quizapp.presentation.login.LoginScreen
import com.example.quizapp.presentation.menu.MenuScreen
import com.example.quizapp.presentation.my_quizzes.MyQuizzesScreen
import com.example.quizapp.presentation.profile.ProfileScreen
import com.example.quizapp.presentation.register.RegisterScreen
import com.example.quizapp.presentation.reset_password.ResetPasswordScreen
import com.example.quizapp.presentation.start.StartScreen
import com.example.quizapp.presentation.util.NestedGraph
import com.example.quizapp.presentation.util.Screen
import com.example.quizapp.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = colorResource(R.color.surface)) {
                    NavHost(
                        navController = navController,
                        startDestination = NestedGraph.AuthGraph.route
                    ) {
                        navigation(
                            startDestination = NestedGraph.AuthGraph.startDestination,
                            route = NestedGraph.AuthGraph.route
                        ) {
                            composable(Screen.StartScreen.route) {
                                StartScreen(navController = navController)
                            }
                            composable(Screen.LoginScreen.route) {
                                LoginScreen(navController = navController)
                            }
                            composable(Screen.RegisterScreen.route) {
                                RegisterScreen(navController = navController)
                            }
                            composable(Screen.ResetPasswordScreen.route) {
                                ResetPasswordScreen(navController = navController)
                            }
                        }
                        navigation(
                            startDestination = NestedGraph.MenuGraph.startDestination,
                            route = NestedGraph.MenuGraph.route
                        ) {

                            composable(Screen.MenuScreen.route) {
                                MenuScreen(navController = navController)
                            }
                            composable(Screen.ProfileScreen.route) {
                                ProfileScreen(navController = navController)
                            }
                            composable(Screen.LibraryScreen.route) {
                                LibraryScreen(navController = navController)
                            }
                            composable(Screen.MyQuizzesScreen.route) {
                                MyQuizzesScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}