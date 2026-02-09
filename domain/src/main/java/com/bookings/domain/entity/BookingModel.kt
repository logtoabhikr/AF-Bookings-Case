package com.bookings.domain.entity

data class Booking(
    val id: String,
    val origin: String,
    val destination: String,
    val imageUrl: String,
    val reference: String,
    val travelerCount: Int,
    val status: BookingStatus,
    val totalDuration: String,
    val departureLabel: String,
    val tripType: String,
    val segments: List<Segment>
)

enum class BookingStatus { UPCOMING, PAST, UNKNOWN }

sealed interface Segment {
    data class Flight(
        val date: String,
        val timeScheduled: String,
        val timeActual: String?,
        val airport: String,
        val statusLabel: String?,
        val isDelayed: Boolean
    ) : Segment

    data class Transfer(
        val duration: String
    ) : Segment
}