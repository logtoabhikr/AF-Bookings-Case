package com.bookings.af.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookings.af.ui.viewstate.BookingsUiState
import com.bookings.domain.usecase.BookingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(private val bookingsUseCase: BookingsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<BookingsUiState>(BookingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchBookings()
    }

    fun fetchBookings() {
        viewModelScope.launch {
            _uiState.value = BookingsUiState.Loading

            bookingsUseCase()
                .onSuccess { data ->
                    _uiState.update { BookingsUiState.Success(data) }
                }
                .onFailure { e ->
                    _uiState.update { BookingsUiState.Error(e.localizedMessage ?: "Unknown Error") }
                }
        }
    }
}