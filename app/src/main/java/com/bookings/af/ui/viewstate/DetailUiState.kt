package com.bookings.af.ui.viewstate

import com.bookings.domain.entity.Booking

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data object Error : DetailUiState
    data class Success(val booking: Booking) : DetailUiState
}