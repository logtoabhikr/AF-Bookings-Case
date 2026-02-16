package com.bookings.af.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bookings.af.R
import com.bookings.af.ui.components.BookingsTabItem
import com.bookings.af.ui.components.ReservationPnrUI
import com.bookings.af.ui.theme.AFTheme
import com.bookings.af.ui.viewmodel.BookingsViewModel
import com.bookings.af.ui.viewstate.BookingsUiState
import com.bookings.domain.entity.Booking
import com.bookings.domain.entity.BookingStatus
import com.bookings.domain.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun BookingsScreen(
    viewModel: BookingsViewModel = hiltViewModel(),
    onBookingClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BookingsScreenUI(
        uiState = uiState,
        onBookingClick = onBookingClick,
        onRetry = { viewModel.fetchBookings() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookingsScreenUI(
    uiState: BookingsUiState,
    onBookingClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val titles = listOf(stringResource(R.string.tab_upcoming), stringResource(R.string.tab_past))

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.title_my_bookings),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AFTheme.dimens.spacingL)
                        .height(AFTheme.dimens.tabsHeight)
                        .background(
                            MaterialTheme.colorScheme.inverseOnSurface,
                            RoundedCornerShape(AFTheme.dimens.spacingM)
                        )
                        .padding(AFTheme.dimens.spacingES)
                ) {
                    Row(Modifier.fillMaxSize()) {
                        titles.forEachIndexed { index, title ->
                            BookingsTabItem(
                                title = title,
                                isSelected = pagerState.currentPage == index,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    scope.launch { pagerState.animateScrollToPage(index) }
                                }
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            val message = stringResource(R.string.msg_start_flow)
            val actionLabel = stringResource(R.string.action_dismiss)
            SmallFloatingActionButton(onClick = {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                }
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.app_name),
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is BookingsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is BookingsUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.error_message, uiState.message))
                        Button(onClick = { onRetry() }) { Text(stringResource(R.string.btn_retry)) }
                    }
                }

                is BookingsUiState.Success -> {
                    BookingsPagerContent(
                        bookings = uiState.bookings,
                        pagerState = pagerState,
                        onClick = onBookingClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BookingsPagerContent(
    bookings: List<Booking>,
    pagerState: PagerState,
    onClick: (String) -> Unit
) {
    HorizontalPager(state = pagerState) { page ->
        val isPastTab = page == 1
        val filtered = bookings.filter {
            if (isPastTab) it.status == BookingStatus.PAST else it.status == BookingStatus.UPCOMING
        }
        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    stringResource(R.string.error_not_found),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(AFTheme.dimens.spacingL),
                verticalArrangement = Arrangement.spacedBy(AFTheme.dimens.spacingL)
            ) {
                items(filtered, key = { it.id }) { booking ->
                    BookingCard(booking, onClick = { onClick(booking.id) })
                }
            }
        }
    }
}

@Composable
private fun BookingCard(booking: Booking, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.shadow(2.dp, RoundedCornerShape(8.dp))
    ) {
        Column {
            AsyncImage(
                model = booking.imageUrl,
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(126.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${booking.origin} to ${booking.destination}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = booking.departureLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ReservationPnrUI(booking.reference)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.app_name),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = booking.travelerCount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Success State - Upcoming")
@Composable
fun PreviewBookingsScreenSuccess() {

    val mockList = listOf(
        Constants.createMockBooking(id = "1", status = BookingStatus.UPCOMING),
        Constants.createMockBooking(id = "2", status = BookingStatus.UPCOMING)
    )
    BookingsScreenUI(
        uiState = BookingsUiState.Success(bookings = mockList),
        onBookingClick = {},
        onRetry = {}
    )
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun PreviewBookingsScreenError() {
    BookingsScreenUI(
        uiState = BookingsUiState.Error(message = stringResource(R.string.error_generic)),
        onBookingClick = {},
        onRetry = {}
    )
}