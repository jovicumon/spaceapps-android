package com.jovicumon.spaceapps.ui.rockets

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jovicumon.spaceapps.data.local.SpaceAppsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Estados de la pantalla de detalle
sealed class RocketDetailUiState {
    data object Loading : RocketDetailUiState()
    data class Success(val rocket: RocketUiModel) : RocketDetailUiState()
    data class Error(val message: String) : RocketDetailUiState()
    data object NotFound : RocketDetailUiState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketDetailScreen(
    rocketId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var uiState by remember { mutableStateOf<RocketDetailUiState>(RocketDetailUiState.Loading) }

    // Cargo el cohete desde Room usando el id que viene por navegación
    LaunchedEffect(rocketId) {
        val db = SpaceAppsDatabase.getInstance(context)
        val dao = db.rocketDao()

        try {
            val entity = withContext(Dispatchers.IO) {
                dao.getRocketById(rocketId)
            }

            uiState = if (entity == null) {
                RocketDetailUiState.NotFound
            } else {
                RocketDetailUiState.Success(
                    RocketUiModel(
                        id = entity.id,
                        name = entity.name,
                        active = entity.active,
                        description = entity.description,
                        firstFlight = entity.firstFlight,
                        successRatePct = entity.successRatePct,
                        wikipedia = entity.wikipedia,
                        country = entity.country,
                        stages = entity.stages,
                        costPerLaunch = entity.costPerLaunch,
                        imageUrl = entity.imageUrl
                    )
                )
            }
        } catch (e: Exception) {
            uiState = RocketDetailUiState.Error(
                "No se ha podido cargar el detalle del cohete."
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del cohete") },
                navigationIcon = {
                    Button(
                        onClick = { onBack() },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            when (val state = uiState) {
                is RocketDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cargando detalle...")
                    }
                }

                is RocketDetailUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(state.message)
                    }
                }

                is RocketDetailUiState.NotFound -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No se ha encontrado el cohete en la base de datos.")
                    }
                }

                is RocketDetailUiState.Success -> {
                    val rocket = state.rocket

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            // Para que la descripción larga pueda hacer scroll en pantallas pequeñas
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Imagen grande del cohete (o placeholder si fuese null)
                        AsyncImage(
                            model = rocket.imageUrl,
                            contentDescription = "Imagen del cohete ${rocket.name}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = rocket.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val estado = if (rocket.active) "Activo" else "Retirado"
                        Text("Estado: $estado")

                        Text("País de origen: ${rocket.country ?: "Desconocido"}")
                        Text("Etapas: ${rocket.stages ?: 0}")
                        Text("Coste por lanzamiento: ${rocket.costPerLaunch ?: 0} USD")
                        Text("Primera fecha de vuelo: ${rocket.firstFlight ?: "Desconocida"}")
                        Text("Porcentaje de éxito: ${rocket.successRatePct ?: 0}%")

                        rocket.description?.let {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(it)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        rocket.wikipedia?.let { url ->
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                }
                            ) {
                                Text("Más info (Wikipedia)")
                            }
                        }
                    }
                }
            }
        }
    }
}
