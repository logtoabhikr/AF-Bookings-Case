package com.bookings.domain.utils


sealed interface Result<out R> {
    data object Loading : Result<Nothing>
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val message: String, val exception: Throwable? = null) : Result<Nothing>
}