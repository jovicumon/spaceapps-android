package com.jovicumon.spaceapps.ui.rockets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketsListScreen(
    onLogout: () -> Unit,
    onRocketClick: (RocketUiModel) -> Unit,
    viewModel: RocketsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var showOnlyActive by remember { mutableStateOf(false) }

    val rockets = when (uiState) {
        is RocketsUiState.Success -> (uiState as RocketsUiState.Success).rockets
        else -> emptyList()
    }

    val filteredRockets = rockets.filter { rocket ->
        val matchesSearch = rocket.name.contains(searchText, ignoreCase = true)
        val matchesActive = if (showOnlyActive) rocket.active else true
        matchesSearch && matchesActive
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SpaceApps") },
                actions = {
                    Button(
                        onClick = { onLogout() },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cerrar sesión")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (uiState) {
                is RocketsUiState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Cargando cohetes...",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                is RocketsUiState.Error -> {
                    val message = (uiState as RocketsUiState.Error).message
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.refreshRockets() }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                is RocketsUiState.Empty -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No se han encontrado cohetes. Intenta actualizar más tarde.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                is RocketsUiState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            label = { Text("Buscar cohete") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Switch(
                                checked = showOnlyActive,
                                onCheckedChange = { showOnlyActive = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Mostrar solo activos")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (filteredRockets.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sin resultados para la búsqueda actual",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredRockets) { rocket ->
                                    RocketItem(
                                        rocket = rocket,
                                        onClick = { onRocketClick(rocket) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RocketItem(
    rocket: RocketUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = rocket.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            val estado = if (rocket.active) "Activo" else "Retirado"
            Text(
                text = estado,
                style = MaterialTheme.typography.bodySmall
            )

            rocket.description?.let { desc ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
