/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettingssample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Color(0xFF3DDC84),
    primaryVariant = Color(0xFF32B56C),
    secondary = Color(0xFFFF9800),
    secondaryVariant = Color(0xFFFF9800)
)

private val DarkColors = darkColors(
    primary = Color(0xFF313131),
    primaryVariant = Color(0xFF202020),
    secondary = Color(0xFFBD7000),
    secondaryVariant = Color(0xFFBD7000),
    onPrimary = Color.White,
    onBackground = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}