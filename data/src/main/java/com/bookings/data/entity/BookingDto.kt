package com.bookings.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookingDto(
    @SerialName("id") val id: String,
    @SerialName("origin") val origin: String,
    @SerialName("destination") val destination: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("reference") val reference: String,
    @SerialName("traveler_count") val travelerCount: Int,
    @SerialName("status") val status: String,
    @SerialName("total_duration") val totalDuration: String,
    @SerialName("departure_label") val departureLabel: String,
    @SerialName("trip_type") val tripType: String,
    @SerialName("trips") val trips: List<TripDto> = emptyList()
)

@Serializable
data class TripDto(
    /** "FLIGHT" or "TRANSFER" **/
    @SerialName("type") val type: String,
    @SerialName("date") val date: String? = null,
    @SerialName("time_scheduled") val timeScheduled: String? = null,
    @SerialName("time_actual") val timeActual: String? = null,
    @SerialName("airport") val airport: String? = null,
    @SerialName("status_label") val statusLabel: String? = null,
    @SerialName("is_delayed") val isDelayed: Boolean? = false,
    @SerialName("duration") val duration: String? = null
)