package com.segunfamisa.zeitung.common.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.segunfamisa.zeitung.common.R

private val raleWayFontFamily = FontFamily(
    Font(R.font.raleway_light, FontWeight.Light),
    Font(R.font.raleway_regular, FontWeight.Normal),
    Font(R.font.raleway_medium, FontWeight.Medium),

    Font(R.font.raleway_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.raleway_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.raleway_medium_italic, FontWeight.Medium, FontStyle.Italic)
)

private val nunitoFamily = FontFamily(
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium),

    Font(R.font.nunito_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.nunito_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunito_medium_italic, FontWeight.Medium, FontStyle.Italic)
)

private val textStyle: TextStyle = TextStyle(
    fontFamily = nunitoFamily
)

internal fun zeitungTypography() = Typography(
    displayLarge = textStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        letterSpacing = (-1.5).sp
    ),
    displayMedium = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = textStyle.copy(
        fontWeight = FontWeight.Light,
        fontSize = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = 0.25.sp
    ),
    headlineMedium = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = textStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleLarge = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = textStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp
    ),
    bodyMedium = textStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    bodySmall = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.5.sp
    ),
    labelMedium = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 1.5.sp
    ),
    labelSmall = textStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp
    )
)

@Composable
fun typography() = zeitungTypography()
