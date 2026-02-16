package com.bookings.domain.utils

import com.bookings.domain.entity.Booking
import com.bookings.domain.entity.BookingStatus
import com.bookings.domain.entity.Trip

object Constants {
    const val BOOKING_TITLE = "Bookings"
    const val ACCOUNT_TITLE = "Account"
    const val ASSET_FILE_BOOKINGS = "bookings.json"
    fun createMockBooking(
        id: String = "1",
        status: BookingStatus = BookingStatus.UPCOMING
    ) = Booking(
        id = id,
        origin = "Paris",
        destination = "San Francisco",
        imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34",
        reference = "REF123",
        travelerCount = 2,
        status = status,
        totalDuration = "2h 30m",
        departureLabel = "Terminal 5",
        tripType = "Round Trip",
        trips = listOf(
            Trip.Flight(
                date = "2026-06-15",
                timeScheduled = "07:05",
                timeActual = "07:01",
                airport = "Pau, Pau Pyrénées Airport",
                statusLabel = "ARRIVED",
                isDelayed = false
            ),
            Trip.Transfer(duration = "30m"),
            Trip.Flight(
                date = "2026-06-15",
                timeScheduled = "06:30",
                timeActual = "06:30",
                airport = "Paris, Paris-Charles de Gaulle airport",
                statusLabel = "ARRIVED",
                isDelayed = true
            ),
        )
    )
}