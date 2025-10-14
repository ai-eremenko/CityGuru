package com.example.cityguru.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium, // или Bold, SemiBold
        fontSize = 18.sp, // размер шрифта
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    )
)