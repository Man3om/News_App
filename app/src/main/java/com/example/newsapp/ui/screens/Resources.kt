package com.example.newsapp.ui.screens

sealed interface Resources<T> {
    class Initial<T> : Resources<T>
    class Loading<T> : Resources<T>
    data class Success<E>(val response: E) : Resources<E>
    data class Error<T>(val message: String) : Resources<T>
}