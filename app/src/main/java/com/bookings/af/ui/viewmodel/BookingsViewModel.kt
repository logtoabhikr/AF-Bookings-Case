package com.bookings.af.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookings.af.ui.viewstate.BookingsUiState
import com.bookings.domain.usecase.BookingUseCase
import com.bookings.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(private val bookingsUseCase: BookingUseCase) :
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
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = BookingsUiState.Loading
                        }

                        is Result.Success -> {
                            _uiState.update { BookingsUiState.Success(result.data) }
                        }

                        is Result.Error -> {
                            _uiState.update { BookingsUiState.Error(result.message) }
                        }
                    }
                }
        }
    }
}