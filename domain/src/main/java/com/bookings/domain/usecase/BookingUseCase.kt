package com.bookings.domain.usecase

import com.bookings.domain.entity.Booking
import com.bookings.domain.repository.AFRepository
import com.bookings.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingUseCase @Inject constructor(private val afRepository: AFRepository) {

    suspend operator fun invoke(): Flow<Result<List<Booking>>> {
        return afRepository.getBookings()
    }
}