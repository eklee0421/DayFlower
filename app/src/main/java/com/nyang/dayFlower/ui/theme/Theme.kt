package com.nyang.dayFlower.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.core.view.WindowCompat

//region dark
private val DarkColorScheme = darkColorScheme(
    primary = Primary60,
    onPrimary = Neutral100,
    primaryContainer = Gray200,
    onPrimaryContainer = Primary20,

    secondary = Secondary60,
    onSecondary = Neutral100,
    secondaryContainer = Secondary99,
    onSecondaryContainer = Secondary20,

    tertiary = Tertiary60,
    onTertiary = Neutral100,
    tertiaryContainer = Tertiary99,
    onTertiaryContainer = Tertiary20,


    background = Neutral100,
    surface = Neutral99,
    surfaceVariant = Neutral95,
    onSurfaceVariant = Neutral80,
    onBackground = Neutral20,
    onSurface = Neutral40,

    error = Tertiary50,

    outline = Neutral95,
    outlineVariant = Neutral90,

    inverseSurface = Neutral20,
    inverseOnSurface = Neutral100,
    inversePrimary = Primary80,

    scrim = Primary30
)
//endregion

private val LightColorScheme = lightColorScheme(
    primary = Primary60,
    onPrimary = Neutral100,
    primaryContainer = Gray200,
    onPrimaryContainer = Primary20,

    secondary = Secondary60,
    onSecondary = Neutral100,
    secondaryContainer = Secondary99,
    onSecondaryContainer = Secondary20,

    tertiary = Tertiary60,
    onTertiary = Neutral100,
    tertiaryContainer = Tertiary99,
    onTertiaryContainer = Tertiary20,


    background = Neutral100,
    surface = Neutral99,
    surfaceVariant = Neutral95,
    onSurfaceVariant = Neutral80,
    onBackground = Neutral20,
    onSurface = Neutral40,

    error = Tertiary50,

    outline = Neutral95,
    outlineVariant = Neutral90,

    inverseSurface = Neutral20,
    inverseOnSurface = Neutral100,
    inversePrimary = Primary80,

    scrim = Primary30
)

@Composable
fun DayFlowerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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