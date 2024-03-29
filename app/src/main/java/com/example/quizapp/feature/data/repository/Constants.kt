package com.example.quizapp.feature.data.repository

object Constants {
    const val COLLECTION_USER_PROFILES = "userProfiles"
    const val COLLECTION_QUIZZES = "quizzes"
    const val COLLECTION_QUESTIONS = "questions"
    const val COLLECTION_USER_QUIZZES = "userQuizzes"

    const val NULL_FIREBASE_USER = "FirebaseUser is null"
    const val NULL_USERNAME_FROM_GOOGLE = "DisplayName from Google account is null"
    const val NO_QUIZ_FOUND = "No quiz found with id: "


}

object Pagers {
    const val QUIZ_PAGER = "QuizPager"
    const val LATEST_QUIZ_PAGER = "LatestQuizPager"
    const val MOST_VIEWED_QUIZ_PAGER = "MostViewedQuizPager"
}