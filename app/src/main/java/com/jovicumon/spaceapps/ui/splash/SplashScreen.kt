package com.jovicumon.spaceapps.ui.splash

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.LocalImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.jovicumon.spaceapps.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    // Mantengo el splash 2 segundos y luego continúo.
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    val context = LocalContext.current

    // ImageLoader propio, forzando soporte GIF
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // GIF arriba
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(R.raw.splash_rocket) // gif en /res/raw
                        .crossfade(true)
                        .build(),
                    contentDescription = "Animación inicial SpaceApps",
                    modifier = Modifier
                        .size(180.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Nombre de la app debajo
                Text(
                    text = "SpaceApps",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
