package com.bookings.domain.usecase

import com.bookings.domain.entity.Booking
import com.bookings.domain.repository.AFRepository
import javax.inject.Inject

class BookingsUseCase @Inject constructor(private val afRepository: AFRepository) {

    suspend operator fun invoke(): Result<List<Booking>> {
        return try {
            val bookings = afRepository.getBookings()
            Result.success(bookings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}