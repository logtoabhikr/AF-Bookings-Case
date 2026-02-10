package com.bookings.af.ui.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bookings.af.R
import com.bookings.af.ui.components.CustomBoldTextView
import com.bookings.af.ui.components.ReservationPnrUI
import com.bookings.af.ui.theme.AFBlue
import com.bookings.af.ui.theme.AFBlueLight
import com.bookings.af.ui.theme.AFTheme
import com.bookings.af.ui.theme.CardGray
import com.bookings.af.ui.theme.DelayRed
import com.bookings.af.ui.theme.SuccessBg
import com.bookings.af.ui.theme.SuccessGreen
import com.bookings.af.ui.theme.SurfaceGray
import com.bookings.af.ui.theme.TextPrimary
import com.bookings.af.ui.theme.TextSecondary
import com.bookings.af.ui.theme.TransferBlue
import com.bookings.af.ui.viewmodel.BookingDetailViewModel
import com.bookings.af.ui.viewstate.BookingDetailUiState
import com.bookings.domain.entity.Booking
import com.bookings.domain.entity.Trip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: BookingDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is BookingDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is BookingDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.error_generic))
            }
        }

        is BookingDetailUiState.Success -> {
            DetailContents(
                booking = state.booking,
                onBack = onBack
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContents(
    booking: Booking,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    val headerAlpha by remember {
        derivedStateOf {
            (1f - (scrollState.value / 600f)).coerceIn(
                0f,
                1f
            )
        }
    }

    val isCollapsed by remember { derivedStateOf { scrollState.value > 300 } }
    val topBarColor by animateColorAsState(
        if (isCollapsed) SurfaceGray else Color.Transparent
    )
    val contentColor by animateColorAsState(
        if (isCollapsed) MaterialTheme.colorScheme.onSurface else Color.White
    )
    Scaffold(
        containerColor = SurfaceGray,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (isCollapsed) {
                        Text(
                            "${booking.origin} - ${booking.destination}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.app_name),
                            tint = contentColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = topBarColor),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.MoreVert,
                            stringResource(R.string.app_name),
                            tint = contentColor
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = booking.imageUrl,
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AFTheme.dimens.headerHeight)
                    .graphicsLayer {
                        alpha = headerAlpha
                        translationY = -scrollState.value * 0.5f
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        clip = true
                    })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = innerPadding.calculateBottomPadding())
            ) {
                Spacer(Modifier.height(AFTheme.dimens.headerHeight - AFTheme.dimens.sheetOverlap))
                Text(
                    text = "${booking.origin}\n${booking.destination}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 32.sp,
                    modifier = Modifier.padding(horizontal = AFTheme.dimens.spacingXL)
                )
                Column(
                    Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .semantics(mergeDescendants = true) { }) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardGray),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(8.dp))
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.label_booking_ref),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                ReservationPnrUI(booking.reference)
                            }

                            Spacer(Modifier.height(12.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier.padding(end = 6.dp),
                                    text = stringResource(
                                        id = R.string.label_departure_ref
                                    ),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = booking.departureLabel,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                                Spacer(Modifier.width(16.dp))
                                Icon(
                                    painter = painterResource(R.drawable.ic_plane),
                                    stringResource(id = R.string.app_name),
                                    modifier = Modifier
                                        .size(24.dp)
                                        .rotate(45f)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(booking.tripType, color = TextSecondary, fontSize = 13.sp)
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    CustomBoldTextView(
                        modifier = Modifier.padding(horizontal = 36.dp),
                        stringResource(id = R.string.label_trip_duration),
                        booking.totalDuration
                    )
                    Spacer(Modifier.height(16.dp))
                    booking.trips.forEachIndexed { index, trip ->
                        TripTimelineRowItem(
                            trip = trip,
                            isFirst = index == 0,
                            isLast = index == booking.trips.lastIndex
                        )
                    }
                    Spacer(Modifier.height(48.dp))
                }
            }
        }
    }
}

@Composable
fun TripTimelineRowItem(trip: Trip, isFirst: Boolean, isLast: Boolean) {
    Row(modifier = Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.Top) {
        TripTimelineNode(
            isFirst = isFirst,
            isLast = isLast,
            isTransfer = trip is Trip.Transfer,
            modifier = Modifier.padding(end = 12.dp)
        )
        Box(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .weight(1f)
        ) {
            when (trip) {
                is Trip.Flight -> FlightCard(trip)
                is Trip.Transfer -> TransferCard(trip)
            }
        }
    }
}

@Composable
fun TripTimelineNode(
    isFirst: Boolean,
    isLast: Boolean,
    isTransfer: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .width(24.dp)
    ) {
        val centerX = size.width / 2
        val strokeWidth = 3.dp.toPx()
        val circleRadius = 5.dp.toPx()
        val lineColor = AFBlue
        val topY = 0f
        val bottomY = size.height
        val circleCenterY = size.height / 2
        if (isTransfer) {
            drawLine(
                color = lineColor,
                start = Offset(centerX, topY),
                end = Offset(centerX, bottomY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(0f, 15f), 0f
                )
            )
        } else {
            if (!isFirst) {
                drawLine(
                    color = lineColor,
                    start = Offset(centerX, topY),
                    end = Offset(centerX, circleCenterY),
                    strokeWidth = strokeWidth
                )
            }
            if (!isLast) {
                drawLine(
                    color = lineColor,
                    start = Offset(centerX, circleCenterY),
                    end = Offset(centerX, bottomY),
                    strokeWidth = strokeWidth
                )
            }
        }
        if (!isTransfer) {
            drawCircle(
                color = Color.White,
                radius = circleRadius,
                center = Offset(centerX, circleCenterY)
            )
            drawCircle(
                color = lineColor,
                radius = circleRadius,
                center = Offset(centerX, circleCenterY),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

@Composable
fun FlightCard(flight: Trip.Flight) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(8.dp))
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(flight.date, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = flight.timeScheduled,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (flight.timeActual != null && flight.timeActual != flight.timeScheduled) TextDecoration.LineThrough else null
                )
                if (flight.timeActual != null) {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = flight.timeActual ?: "",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (flight.isDelayed) DelayRed else SuccessGreen
                    )
                }
            }
            Text(flight.airport, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            if (flight.statusLabel != null) {
                Spacer(Modifier.height(4.dp))
                Surface(
                    color = if (flight.isDelayed) Color(0xFFFFEBEE) else SuccessBg,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = flight.statusLabel ?: "",
                        color = if (flight.isDelayed) DelayRed else SuccessGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TransferCard(transfer: Trip.Transfer) {
    Surface(
        color = TransferBlue,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, AFBlueLight),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        CustomBoldTextView(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            stringResource(R.string.label_transfer_time),
            transfer.duration
        )
    }
}
