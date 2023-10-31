package com.example.quizapp.presentation.util

sealed class Screen(val route: String) {
    object StartScreen : Screen("start_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object ResetPasswordScreen : Screen("reset_password_screen")
    object MenuScreen : Screen("menu_screen")
    object CreateQuizScreen : Screen("create_quiz_screen")
    object CreateQuestionScreen : Screen("create_question_screen")

}
