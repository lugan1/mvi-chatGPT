package com.example.mvi_chatgpt.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.example.mvi_chatgpt.R

/**
 * displayLarge: 57 sp / 64 line height
 * displayMedium: 45 sp / 52 line height
 * displaySmall: 36 sp / 44 line height
 * headlineLarge: 32 sp / 40 line height
 * headlineMedium: 28 sp / 36 line height
 * headlineSmall: 24 sp / 32 line height
 * titleLarge: 20 sp / 28 line height
 * titleMedium: 18 sp / 26 line height
 * titleSmall: 14 sp / 20 line height
 * bodyLarge: 16 sp / 24 line height
 * bodyMedium: 14 sp / 20 line height
 * bodySmall: 12 sp / 16 line height
 * labelLarge: 14 sp / 20 line height
 * labelMedium: 12 sp / 16 line height
 * labelSmall: 11 sp / 16 line height
 * */
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal).toFontFamily(),
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)

val appleGothic = FontFamily(
    Font(R.font.apple_sdgothic_neo_b, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.apple_sdgothic_neo_m, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.apple_sdgothic_neo_r, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.apple_sdgothic_neo_l, FontWeight.Light, FontStyle.Normal),
    Font(R.font.apple_sdgothic_neo_t, FontWeight.Thin, FontStyle.Normal),
)
