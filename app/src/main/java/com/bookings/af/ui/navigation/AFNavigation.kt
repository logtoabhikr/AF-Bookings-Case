package com.bookings.af.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bookings.af.R
import com.bookings.af.ui.view.BookingsScreen
import com.bookings.af.ui.view.DetailScreen

@Composable
fun AFNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AFRoute.Booking
    ) {
        composable<AFRoute.Booking> {
            BookingsScreen(
                onBookingClick = { bookingId ->
                    navController.navigate(DetailRoute(bookingId))
                }
            )
        }
        composable<AFRoute.Account> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.label_account_msg),
                    textAlign = TextAlign.Center
                )
            }
        }
        composable<DetailRoute> {
            DetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}