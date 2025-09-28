package uvg.plats.laboratorio7.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    useDarkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val lightScheme = lightColorScheme(
        primary = Color(0xFF2E7D32),
        onPrimary = Color.White,
        background = Color(0xFFF1F8E9),
        surface = Color(0xFFF1F8E9),
        surfaceVariant = Color(0xFFE7F3E6),
        onSurfaceVariant = Color(0xFF5A6B58),
        outline = Color(0xFFB5C1B4),
        outlineVariant = Color(0xFFB5C1B4)
    )
    val darkScheme = darkColorScheme(
        primary = Color(0xFF2E7D32),
        onPrimary = Color.White,
        background = Color(0xFF0F1E0F),
        surface = Color(0xFF0F1E0F),
        surfaceVariant = Color(0xFF1A2D1A),
        onSurfaceVariant = Color(0xFFBFCABF),
        outline = Color(0xFF3A4B3A),
        outlineVariant = Color(0xFF3A4B3A)
    )
    MaterialTheme(
        colorScheme = if (useDarkTheme) darkScheme else lightScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
