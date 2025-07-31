package job.challenge.movieapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0073FF),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF005BBB),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xAA6797C6),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0x77004490),
    onSecondaryContainer = Color(0xFFFFFFFF),

    surface = Color(0xFF151313),
    onSurface = Color(0xFFFFFFFF),

    error = Color(0xFFB00020),
    onError = Color(0xFFFFA8A8),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFF2E000B),

    outline = Color(0xFF36424B),
    outlineVariant = Color(0xFFDFDFDF),

    scrim = Color(0xFF737373),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0073FF),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF005BBB),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xAA6797C6),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0x77004490),
    onSecondaryContainer = Color(0xFFFFFFFF),

    surface = Color(0xFFEAEAEA),
    onSurface = Color(0xFF000000),

    error = Color(0xFFB00020),
    onError = Color(0xFFFFA8A8),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFF2E000B),

    outline = Color(0xFF36424B),
    outlineVariant = Color(0xFFDFDFDF),

    scrim = Color(0xFF737373),
)

/** https://developer.android.com/develop/ui/compose/designsystems/material3 */
@Composable
fun AppTheme(
    enableDarkTheme: Boolean = isSystemInDarkTheme(),
    useSystemDefault: Boolean = false, //only available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useSystemDefault && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (enableDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        enableDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}