package com.bookings.af.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.bookings.af.ui.theme.TextSecondary

@Composable
fun CustomBoldTextView(modifier: Modifier, txtResourceId: String, boldTxt: String) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = TextSecondary)) {
                append(txtResourceId)
            }
            append(" ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(boldTxt)
            }
        },
        fontSize = 14.sp
    )
}