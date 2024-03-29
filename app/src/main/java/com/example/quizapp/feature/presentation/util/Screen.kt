package com.example.quizapp.feature.presentation.util

sealed class Screen(val route: String) {
    object StartScreen : Screen("start_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object ResetPasswordScreen : Screen("reset_password_screen")
    object MenuScreen : Screen("menu_screen")
    object CreateQuizScreen : Screen("create_quiz_screen")
    object CreateQuestionScreen : Screen("create_question_screen")
    object ProfileScreen : Screen("profile_screen")
    object MyQuizzesScreen : Screen("my_quizzes_screen")
    object LibraryScreen : Screen("library_screen")
    object MenuQuizScreen : Screen("menu_quiz_screen")
    object SolveQuizScreen : Screen("solve_quiz_screen")
    object SubmitQuizScreen : Screen("submit_quiz")
    object MostViewedQuizzesScreen : Screen("most_viewed_quizzes")
    object TheLatestQuizzesScreen : Screen("the_latest_quizzes")


}
