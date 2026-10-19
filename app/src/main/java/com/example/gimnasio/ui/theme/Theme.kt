package com.example.gimnasio.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GimnasioColorScheme = darkColorScheme(
    primary = Champagne,
    onPrimary = Carbon,
    secondary = Esmeralda,
    onSecondary = Color.White,
    tertiary = Coral,
    onTertiary = Carbon,
    background = Carbon,
    onBackground = Color.White,
    surface = Tarjeta,
    onSurface = Color.White,
    surfaceVariant = TarjetaClara,
    onSurfaceVariant = TextoSuave,
    error = Rojo,
    onError = Color.White
)

@Composable
fun GIMNASIOTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GimnasioColorScheme,
        typography = Typography,
        content = content
    )
}
