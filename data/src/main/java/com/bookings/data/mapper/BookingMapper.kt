package com.bookings.data.mapper

import com.bookings.data.entity.BookingDto
import com.bookings.data.entity.TripDto
import com.bookings.domain.entity.Booking
import com.bookings.domain.entity.BookingStatus
import com.bookings.domain.entity.Trip

fun BookingDto.toDomain(): Booking {
    return Booking(
        id = id,
        origin = origin,
        destination = destination,
        imageUrl = imageUrl,
        reference = reference,
        travelerCount = travelerCount,
        status = when (status.uppercase()) {
            "UPCOMING" -> BookingStatus.UPCOMING
            "PAST" -> BookingStatus.PAST
            else -> BookingStatus.UNKNOWN
        },
        totalDuration = totalDuration,
        departureLabel = departureLabel,
        tripType = tripType,
        trips = trips.mapNotNull { it.toDomain() }
    )
}

fun TripDto.toDomain(): Trip? {
    return when (type.uppercase()) {
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
