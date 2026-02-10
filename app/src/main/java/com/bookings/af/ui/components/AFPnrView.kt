package com.bookings.af.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bookings.af.ui.theme.AFBlue

@Composable
fun ReservationPnrUI(txt: String) = Surface(
    color = AFBlue,
    shape = CircleShape,
    modifier = Modifier.height(28.dp)
) {
    Text(
        text = txt,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .wrapContentHeight()
    )
}