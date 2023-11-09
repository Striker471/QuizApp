package com.example.quizapp.feature.domain.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val errorData: Any? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}