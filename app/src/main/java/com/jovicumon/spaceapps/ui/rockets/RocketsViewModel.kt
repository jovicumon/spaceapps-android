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
    val wikipedia: String?,
    val country: String?,
    val stages: Int?,
    val costPerLaunch: Long?,
    val imageUrl: String?
)

class RocketsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RocketRepository

    private val _uiState = MutableStateFlow<RocketsUiState>(RocketsUiState.Loading)
    val uiState: StateFlow<RocketsUiState> = _uiState.asStateFlow()

    init {
        val db = SpaceAppsDatabase.getInstance(application)
        val dao = db.rocketDao()
        val api = SpaceXApiClient.api
        repository = RocketRepository(api, dao)

        loadRockets()
    }

    fun refreshRockets() {
        loadRockets()
    }

    private fun loadRockets() {
        viewModelScope.launch {
            _uiState.value = RocketsUiState.Loading

            // Hackito para ver bien la pantalla de carga
            delay(2000)

            try {
                repository.refreshRockets()
                val entities = repository.getRocketsOnce()

                if (entities.isEmpty()) {
                    _uiState.value = RocketsUiState.Empty
                } else {
                    _uiState.value = RocketsUiState.Success(
                        entities.map { it.toUiModel() }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = RocketsUiState.Error(
                    "Error al cargar. Verifica tu conexión."
                )
            }
        }
    }

    private fun RocketEntity.toUiModel(): RocketUiModel =
        RocketUiModel(
            id = id,
            name = name,
            active = active,
            description = description,
            firstFlight = firstFlight,
            successRatePct = successRatePct,
            wikipedia = wikipedia,
            country = country,
            stages = stages,
            costPerLaunch = costPerLaunch,
            imageUrl = imageUrl
        )
}
