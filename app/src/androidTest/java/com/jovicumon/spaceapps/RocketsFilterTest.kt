package com.jovicumon.spaceapps

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jovicumon.spaceapps.MainActivity
import org.junit.Rule
import org.junit.Test

class RocketsFilterTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun onlyActiveFilter_showsOnlyActiveRockets() {
        // Espero a que aparezca la pantalla de login
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithTag("emailField")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // LOGIN con tags
        composeTestRule.onNodeWithTag("emailField")
            .performTextInput("admin@lasalle.es")

        composeTestRule.onNodeWithTag("passwordField")
            .performTextInput("admin1234")

        composeTestRule.onNodeWithTag("loginButton")
            .performClick()

        // Espero a que desaparezca el loading de la lista
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("Cargando cohetes...")
                .fetchSemanticsNodes().isEmpty()
        }

        // Activo el filtro de "Mostrar solo activos"
        composeTestRule.onNodeWithTag("activeSwitch")
            .performClick()

        // Compruebo que no hay ningún cohete marcado como "Retirado"
        composeTestRule.onAllNodesWithText("Retirado")
            .assertCountEquals(0)
    }
}
