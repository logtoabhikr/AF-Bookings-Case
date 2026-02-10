package com.bookings.af.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bookings.af.R
import com.bookings.af.ui.view.BookingsScreen
import com.bookings.af.ui.view.DetailScreen
import com.bookings.domain.utils.Constants

enum class AppDestinations(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    Booking("booking_route", Constants.BOOKING_TITLE, Icons.Default.DateRange),
    Settings("settings_route", Constants.ACCOUNT_TITLE, Icons.Default.Person)
}

@Composable
fun AFNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
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
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.label_account_msg),
                    textAlign = TextAlign.Center
                )
            }
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