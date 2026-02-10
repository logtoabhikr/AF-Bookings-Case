package com.bookings.data.mapper

import com.bookings.data.entity.BookingDto
import com.bookings.data.entity.TripDto
import com.bookings.domain.entity.Booking
import com.bookings.domain.entity.BookingStatus
import com.bookings.domain.entity.Trip

fun BookingDto.toDomain(): Booking {
    return Booking(
        id = this.id,
        origin = this.origin,
        destination = this.destination,
        imageUrl = this.imageUrl,
        reference = this.reference,
        travelerCount = this.travelerCount,
        status = when (this.status.uppercase()) {
            "UPCOMING" -> BookingStatus.UPCOMING
            "PAST" -> BookingStatus.PAST
            else -> BookingStatus.UNKNOWN
        },
        totalDuration = this.totalDuration,
        departureLabel = this.departureLabel,
        tripType = this.tripType,
        trips = this.trips.mapNotNull { it.toDomain() }
    )
}

fun TripDto.toDomain(): Trip? {
    return when (this.type.uppercase()) {
        "FLIGHT" -> {
            if (date != null && timeScheduled != null && airport != null) {
                Trip.Flight(
                    date = date,
                    timeScheduled = timeScheduled,
                    timeActual = timeActual,
                    airport = airport,
                    statusLabel = statusLabel,
                    isDelayed = isDelayed ?: false
                )
            } else {
                null
            }
        }
        "TRANSFER" -> {
            if (duration != null) {
                Trip.Transfer(duration = duration)
            } else {
                null
            }
        }
        else -> null
    }
}
