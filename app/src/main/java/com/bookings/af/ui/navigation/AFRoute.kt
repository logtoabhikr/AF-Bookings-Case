package com.bookings.af.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.bookings.domain.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
enum class AFDestinations(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    Booking(AFRoute.Booking, Constants.BOOKING_TITLE, Icons.Default.DateRange),
    Account(AFRoute.Account, Constants.ACCOUNT_TITLE, Icons.Default.Person)
}

@Serializable
sealed class AFRoute {
    @Serializable
    data object Booking : AFRoute()
    @Serializable
    data object Account : AFRoute()
}

@Serializable
data class DetailRoute(val bookingId: String)
