package com.bookings.domain.usecase

import com.bookings.domain.entity.Booking
import com.bookings.domain.repository.AFRepository
import com.bookings.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingDetailUseCase @Inject constructor(private val afRepository: AFRepository) {

    suspend operator fun invoke(bookingID: String): Flow<Result<Booking>> {
        return afRepository.getBookingById(bookingID)
    }
}