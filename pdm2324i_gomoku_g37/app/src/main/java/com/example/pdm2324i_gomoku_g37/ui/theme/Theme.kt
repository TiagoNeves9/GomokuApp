package com.example.pdm2324i_gomoku_g37.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.pdm2324i_gomoku_g37.screens.login.LoginScreen
import com.example.pdm2324i_gomoku_g37.screens.main.MainScreen


private val darkColorScheme = darkColorScheme(
    primary = Blue30, //Default button/notification bar color
    secondary = Blue40,
    tertiary = Grey20,
    background = BackgroundBlue,
    onBackground = Grey20,
    onPrimaryContainer = Grey20,
    surfaceVariant = Blue80
)

private val lightColorScheme = lightColorScheme(
    primary = PrimaryBlue, //Default button/notification bar color
    secondary = SecondaryBlue,
    tertiary = TertiaryBlue,
    background = PrimaryBlue,
    onBackground = Grey10,
    onPrimaryContainer = Grey10

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val JokesAppIcons = Icons.Outlined

@Composable
fun GomokuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Preview
@Composable
fun GomokuDarkThemePreview() {
    GomokuTheme(darkTheme = true) {
        LoginScreen()
    }
}

@Preview
@Composable
fun GomokuLightThemePreview() {
    GomokuTheme(darkTheme = false) {
        LoginScreen()
    }
}