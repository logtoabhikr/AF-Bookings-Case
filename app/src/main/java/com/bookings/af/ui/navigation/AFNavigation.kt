package com.bookings.af.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bookings.af.ui.view.BookingsScreen
import com.bookings.af.ui.view.DetailScreen
import com.bookings.domain.utils.Constants

enum class AppDestinations(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Booking("booking_route", Constants.BOOKING_TITLE, Icons.Default.DateRange),
    Settings("settings_route", Constants.ACCOUNT_TITLE, Icons.Default.Person),
}

@Composable
fun AFNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Booking.route
    ) {
        composable(AppDestinations.Booking.route) {
            BookingsScreen(
                onBookingClick = { bookingId ->
                    navController.navigate("detail/$bookingId")
                }
            )
        }
        composable(AppDestinations.Settings.route) {
            Box(modifier.fillMaxSize()) { Text(Constants.ACCOUNT_TITLE) }
        }
        composable(
            route = "detail/{bookingId}",
            arguments = listOf(navArgument(Constants.ARG_BOOKING_ID) { type = NavType.StringType })
        ) {
            DetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}