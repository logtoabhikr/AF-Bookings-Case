package com.bookings.af.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bookings.af.ui.theme.AFBlue

@Composable
fun BookingsTabItem(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .then(
                if (isSelected) {
                    Modifier
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(6.dp))
                        .background(Color.White, RoundedCornerShape(6.dp))
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
    ) {
        Text(
            modifier = Modifier.semantics { contentDescription = title },
            text = title,
            color = if (isSelected) AFBlue else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}