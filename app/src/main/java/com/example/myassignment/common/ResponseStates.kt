package com.example.myassignment.common

sealed class ResponseStates<T>(
    val data: T? = null,
    val msg: String? = null,
) {
    class Success<T>(data: T):ResponseStates<T>(data)
    class Loading<T>(data: T?):ResponseStates<T>(data)
    class Error<T>(msg: String?):ResponseStates<T>( msg = msg)
}