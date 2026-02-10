package com.bookings.af.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = AFBlue,
    onPrimary = Color.White,
    primaryContainer = AFBlueLight,
    background = SurfaceGray,
    onBackground = TextPrimary,
    surface = CardGray,
    onSurface = TextPrimary,
    surfaceVariant = TransferBlue,
    onSurfaceVariant = TextSecondary,
    error = DelayRed,
    errorContainer = DelayBg,
    onError = Color.White,
    tertiary = SuccessGreen,
    tertiaryContainer = SuccessBg,
    onTertiary = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = AFBlueDark,
    onPrimary = AFBlue,
    primaryContainer = AFBlueLight,
    background = SurfaceDark,
    onBackground = TextPrimaryDark,
    surface = CardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = TransferBlueDark,
    onSurfaceVariant = TextSecondaryDark,
    error = DelayRedDark,
    errorContainer = DelayBgDark,
    tertiary = SuccessGreenDark,
    tertiaryContainer = SuccessBgDark
)

val LocalAFDimens = staticCompositionLocalOf {
    AFDimens()
}

object AFTheme {
    val dimens: AFDimens
        @Composable
        @ReadOnlyComposable
        /** Optimization hint for compiler **/
        get() = LocalAFDimens.current
}

@Composable
fun AFBookingsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val dimens = AFDimens()
    CompositionLocalProvider(LocalAFDimens provides dimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}