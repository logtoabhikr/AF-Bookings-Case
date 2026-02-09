package com.bookings.af.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookings.af.ui.viewstate.DetailUiState
import com.bookings.domain.repository.AFRepository
import com.bookings.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val afRepository: AFRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookingId: String = checkNotNull(savedStateHandle[Constants.ARG_BOOKING_ID])
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchBooking()
    }

    private fun fetchBooking() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            val booking = afRepository.getBookingById(bookingId)
            if (booking != null) {
                _uiState.value = DetailUiState.Success(booking)
            } else {
                _uiState.value = DetailUiState.Error
            }
        }
    }
}