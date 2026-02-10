package com.bookings.af.ui.viewstate

import com.bookings.domain.entity.Booking

internal sealed interface BookingDetailUiState {
    data object Loading : BookingDetailUiState
    data object Error : BookingDetailUiState
    data class Success(val booking: Booking) : BookingDetailUiState
}