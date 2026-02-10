package com.bookings.domain.repository

import com.bookings.domain.entity.Booking
import kotlinx.coroutines.flow.Flow
import com.bookings.domain.utils.Result

interface AFRepository {
    suspend fun getBookings(): Flow<Result<List<Booking>>>
    suspend fun getBookingById(id: String): Flow<Result<Booking>>
}