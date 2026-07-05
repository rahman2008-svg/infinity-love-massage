package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = SophisticatedPrimary,
    onPrimary = SophisticatedOnPrimary,
    secondary = SophisticatedSecondary,
    onSecondary = SophisticatedOnSecondary,
    tertiary = SophisticatedTertiary,
    onTertiary = SophisticatedOnTertiary,
    background = SophisticatedBackground,
    onBackground = SophisticatedText,
    surface = SophisticatedSurface,
    onSurface = SophisticatedText,
    surfaceVariant = SophisticatedSurfaceVariant,
    onSurfaceVariant = SophisticatedText,
    outline = SophisticatedPrimary.copy(alpha = 0.2f)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SophisticatedOnPrimary, // Deep burgundy as primary in light theme
    onPrimary = SophisticatedPrimary, // Soft rose text
    secondary = SophisticatedSurfaceVariant,
    onSecondary = SophisticatedText,
    tertiary = SophisticatedPrimary,
    onTertiary = SophisticatedOnPrimary,
    background = Color(0xFFFFF2F4), // Soft cream blush
    onBackground = SophisticatedOnPrimary,
    surface = Color.White,
    onSurface = SophisticatedOnPrimary,
    surfaceVariant = Color(0xFFFFE0E5),
    onSurfaceVariant = SophisticatedOnPrimary,
    outline = SophisticatedOnPrimary.copy(alpha = 0.15f)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disable dynamic color by default for cohesive branding
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
