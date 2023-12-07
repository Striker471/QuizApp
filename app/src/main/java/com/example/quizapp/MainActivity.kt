package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.feature.presentation.auth_graph.login.LoginScreen
import com.example.quizapp.feature.presentation.auth_graph.register.RegisterScreen
import com.example.quizapp.feature.presentation.auth_graph.reset_password.ResetPasswordScreen
import com.example.quizapp.feature.presentation.auth_graph.start.StartScreen
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionScreen
import com.example.quizapp.feature.presentation.menu_graph.create_quiz.CreateQuizScreen
import com.example.quizapp.feature.presentation.menu_graph.library.LibraryScreen
import com.example.quizapp.feature.presentation.menu_graph.menu.MenuScreen
import com.example.quizapp.feature.presentation.menu_graph.menu_quiz.MenuQuizScreen
import com.example.quizapp.feature.presentation.menu_graph.most_viewed_quizzes.MostViewedQuizzesScreen
import com.example.quizapp.feature.presentation.menu_graph.my_quizzes.MyQuizzesScreen
import com.example.quizapp.feature.presentation.menu_graph.profile.ProfileScreen
import com.example.quizapp.feature.presentation.menu_graph.solve_quiz.SolveQuizScreen
import com.example.quizapp.feature.presentation.menu_graph.submit_quiz.SubmitQuizScreen
import com.example.quizapp.feature.presentation.menu_graph.the_latest_quizzes.TheLatestQuizzesScreen
import com.example.quizapp.feature.presentation.util.NestedGraph
import com.example.quizapp.feature.presentation.util.Screen
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
                            composable(Screen.CreateQuizScreen.route) {
                                CreateQuizScreen(navController = navController)
                            }
                            composable(
                                route = Screen.CreateQuestionScreen.route +
                                        "?quizId={quizId}",
                                arguments = listOf(
                                    navArgument(
                                        name = "quizId"
                                    ) {
                                        type = NavType.StringType
                                        defaultValue = ""
                                    }
                                )
                            ) {
                                CreateQuestionScreen(navController = navController)
                            }
                            composable(
                                route = Screen.MenuQuizScreen.route +
                                        "?quizId={quizId}",
                                arguments = listOf(
                                    navArgument(
                                        name = "quizId"
                                    ) {
                                        type = NavType.StringType
                                        defaultValue = ""
                                    }
                                )
                            ) {
                                MenuQuizScreen(navController = navController)
                            }
                            composable(
                                route = Screen.SolveQuizScreen.route +
                                        "?quizId={quizId}",
                                arguments = listOf(
                                    navArgument(
                                        name = "quizId"
                                    ) {
                                        type = NavType.StringType
                                        defaultValue = ""
                                    }
                                )
                            ) {
                                SolveQuizScreen(navController = navController)
                            }
                            composable(
                                route = Screen.SubmitQuizScreen.route +
                                        "?score={score}",
                                arguments = listOf(
                                    navArgument(
                                        name = "score"
                                    ) {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                )
                            ) {
                                val score = it.arguments?.getInt("score") ?: 0
                                SubmitQuizScreen(
                                    navController = navController,
                                    score = score
                                )
                            }
                            composable(Screen.TheLatestQuizzesScreen.route) {
                                TheLatestQuizzesScreen(navController = navController)
                            }
                            composable(Screen.MostViewedQuizzesScreen.route) {
                                MostViewedQuizzesScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}