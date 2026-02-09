package com.bookings.af.ui.viewstate

import com.bookings.domain.entity.Booking

sealed interface BookingsUiState {
    data object Loading : BookingsUiState
    data class Success(val bookings: List<Booking>) : BookingsUiState
    data class Error(val message: String) : BookingsUiState
}

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data object Error : DetailUiState
    data class Success(val booking: Booking) : DetailUiState
}