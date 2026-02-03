// File: app/src/main/java/com/example/buoicuoi/ui/theme/Theme.kt
package com.example.buoicuoi.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Bảng màu cho Chế độ Sáng (Light Mode)
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
    /* Bạn có thể tùy chỉnh các màu khác ở đây */
)

// Bảng màu cho Chế độ Tối (Dark Mode)
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
    /* Bạn có thể tùy chỉnh các màu khác ở đây */
)

// ĐÂY LÀ HÀM QUAN TRỌNG MÀ MAINACTIVITY ĐANG TÌM KIẾM
@Composable
fun BuoiCuoiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Hàm này lấy từ file Type.kt
        content = content
    )
}
