package com.jovicumon.spaceapps.ui.rockets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jovicumon.spaceapps.data.local.RocketEntity
import com.jovicumon.spaceapps.data.local.SpaceAppsDatabase
import com.jovicumon.spaceapps.data.remote.SpaceXApiClient
import com.jovicumon.spaceapps.data.repository.RocketRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados posibles de la pantalla de cohetes.
sealed class RocketsUiState {
    data object Loading : RocketsUiState()
    data class Success(val rockets: List<RocketUiModel>) : RocketsUiState()
    data object Empty : RocketsUiState()
    data class Error(val message: String) : RocketsUiState()
}

// Modelo que usa directamente la UI (lista + detalle).
data class RocketUiModel(
    val id: String,
    val name: String,
    val active: Boolean,
    val description: String?,
    val firstFlight: String?,
    val successRatePct: Int?,
    val wikipedia: String?
)

class RocketsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RocketRepository

    private val _uiState = MutableStateFlow<RocketsUiState>(RocketsUiState.Loading)
    val uiState: StateFlow<RocketsUiState> = _uiState.asStateFlow()

    init {
        // Inicializo repo con la API y la base de datos local
        val db = SpaceAppsDatabase.getInstance(application)
        val dao = db.rocketDao()
        val api = SpaceXApiClient.api
        repository = RocketRepository(api, dao)

        // Nada más crear el ViewModel, cargo los cohetes
        loadRockets()
    }

    fun refreshRockets() {
        // Reutilizo la misma lógica tanto al iniciar como al pulsar "Reintentar"
        loadRockets()
    }

    private fun loadRockets() {
        viewModelScope.launch {
            // 1) Pongo estado Loading
            _uiState.value = RocketsUiState.Loading

            // 2) Truco para ver bien la pantalla de carga
            delay(2000)

            try {
                // 3) Actualizo desde la API y guardo en Room
                repository.refreshRockets()

                // 4) Leo los cohetes que hay ahora en la base de datos
                val entities = repository.getRocketsOnce()

                // 5) Decido el estado final
                if (entities.isEmpty()) {
                    _uiState.value = RocketsUiState.Empty
                } else {
                    _uiState.value = RocketsUiState.Success(
                        entities.map { it.toUiModel() }
                    )
                }
            } catch (e: Exception) {
                // Si hay cualquier problema, muestro error
                _uiState.value = RocketsUiState.Error(
                    "No se han podido actualizar los cohetes. Revisa tu conexión."
                )
            }
        }
    }

    // Conversión de Entity (Room) a modelo de UI
    private fun RocketEntity.toUiModel(): RocketUiModel =
        RocketUiModel(
            id = this.id,
            name = this.name,
            active = this.active,
            description = this.description,
            firstFlight = this.firstFlight,
            successRatePct = this.successRatePct,
            wikipedia = this.wikipedia
        )
}
