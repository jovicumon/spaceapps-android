package com.jovicumon.spaceapps

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jovicumon.spaceapps.ui.rockets.RocketUiModel
import com.jovicumon.spaceapps.ui.rockets.RocketsListContent
import com.jovicumon.spaceapps.ui.rockets.RocketsUiState
import org.junit.Rule
import org.junit.Test

class RocketsErrorRetryTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorState_showsMessage_andRetryLoadsList() {
        // 1) Empiezo en estado Error
        var currentState by mutableStateOf<RocketsUiState>(
            RocketsUiState.Error("Error al cargar. Verifica tu conexión.")
        )

        // 2) Componemos la UI con RocketsListContent
        composeTestRule.setContent {
            RocketsListContent(
                uiState = currentState,
                onLogout = {},
                onRetry = {
                    // 3) Simulo que al pulsar "Reintentar" la carga va bien
                    currentState = RocketsUiState.Success(
                        rockets = listOf(
                            RocketUiModel(
                                id = "fake-id",
                                name = "Falcon Test",
                                active = true,
                                description = "Cohete de prueba para el test de UI.",
                                firstFlight = "2024-01-01",
                                successRatePct = 100,
                                wikipedia = null,
                                country = "TestLand",
                                stages = 2,
                                costPerLaunch = 123456789,
                                imageUrl = null
                            )
                        )
                    )
                },
                onRocketClick = {}
            )
        }

        // 4) Compruebo que se muestra el mensaje de error
        composeTestRule.onNodeWithText("Error al cargar. Verifica tu conexión.")
            .assertIsDisplayed()

        // 5) Compruebo que el botón "Reintentar" existe
        composeTestRule.onNodeWithText("Reintentar")
            .assertIsDisplayed()

        // 6) Pulso el botón "Reintentar"
        composeTestRule.onNodeWithText("Reintentar")
            .performClick()

        // 7) Ahora debería mostrarse la lista con el cohete "Falcon Test"
        composeTestRule.onNodeWithText("Falcon Test")
            .assertIsDisplayed()
    }
}
