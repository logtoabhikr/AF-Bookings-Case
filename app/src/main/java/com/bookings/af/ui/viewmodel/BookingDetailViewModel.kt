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
    val uiState = _uiState.asStateFlow()

    init {
        fetchBooking()
    }

    fun fetchBooking() {
        viewModelScope.launch {
            viewModelScope.launch {
                bookingDetailUseCase(bookingId)
                    .collect { result ->
                        when(result) {
                            is Result.Loading -> _uiState.value = BookingDetailUiState.Loading
                            is Result.Success -> _uiState.value = BookingDetailUiState.Success(result.data)
                            is Result.Error -> _uiState.value = BookingDetailUiState.Error
                        }
                    }
            }
        }
    }
}