package com.bookings.data.mapper

import com.bookings.data.entity.BookingDto
import com.bookings.data.entity.SegmentDto
import com.bookings.domain.entity.Booking
import com.bookings.domain.entity.BookingStatus
import com.bookings.domain.entity.Segment

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
        segments = this.segments.mapNotNull { it.toDomain() }
    )
}

fun SegmentDto.toDomain(): Segment? {
    return when (this.type.uppercase()) {
        "FLIGHT" -> {
            if (date != null && timeScheduled != null && airport != null) {
                Segment.Flight(
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
                Segment.Transfer(duration = duration)
            } else {
                null
            }
        }
        else -> null
    }
}
