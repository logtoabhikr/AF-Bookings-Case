package com.bookings.af.ui.viewstate

import com.bookings.domain.entity.Booking

internal sealed interface BookingsUiState {
    data object Loading : BookingsUiState
    data class Success(val bookings: List<Booking>) : BookingsUiState
    data class Error(val message: String) : BookingsUiState
}