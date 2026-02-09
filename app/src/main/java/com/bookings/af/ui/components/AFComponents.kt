package com.bookings.af.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookings.af.ui.theme.AFBlue
import com.bookings.af.ui.theme.TextSecondary

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

@Composable
fun ReferencePnrView(txt: String) = Surface(
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

@Composable
fun NormalTextWithBold(modifier: Modifier, txt: String, boldTxt: String) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = TextSecondary)) {
                append(txt)
            }
            append(" ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(boldTxt)
            }
        },
        fontSize = 14.sp
    )
}