package com.bookings.domain.repository

import com.bookings.domain.entity.Booking

interface AFRepository {
    suspend fun getBookings(): List<Booking>
    suspend fun getBookingById(id: String): Booking?
}