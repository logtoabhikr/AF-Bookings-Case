package com.bookings.af.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookings.af.ui.viewstate.BookingDetailUiState
import com.bookings.domain.usecase.BookingDetailUseCase
import com.bookings.domain.utils.Constants
import com.bookings.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BookingDetailViewModel @Inject constructor(
    private val bookingDetailUseCase: BookingDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookingId: String = checkNotNull(savedStateHandle[Constants.ARG_BOOKING_ID])
    private val _uiState = MutableStateFlow<BookingDetailUiState>(BookingDetailUiState.Loading)
    internal val uiState = _uiState.asStateFlow()

    init {
        fetchBooking()
    }

    fun fetchBooking() {
        viewModelScope.launch {
            bookingDetailUseCase(bookingId)
                .collect { result ->
                    _uiState.value = when (result) {
                        is Result.Loading -> BookingDetailUiState.Loading
                        is Result.Success -> BookingDetailUiState.Success(result.data)
                        is Result.Error -> BookingDetailUiState.Error
                    }
                }
        }
    }
}