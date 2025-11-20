package com.jovicumon.spaceapps

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jovicumon.spaceapps.MainActivity
import org.junit.Rule
import org.junit.Test

class LoginUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginCorrect_navigatesToRocketsList() {
        // Espero a que aparezca la pantalla de login (campo email visible)
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithTag("emailField")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Relleno el email
        composeTestRule.onNodeWithTag("emailField")
            .performTextInput("admin@lasalle.es")

        // Relleno la contraseña
        composeTestRule.onNodeWithTag("passwordField")
            .performTextInput("admin1234")

        // Pulso el botón de login
        composeTestRule.onNodeWithTag("loginButton")
            .performClick()

        // Compruebo que aparece el texto de carga de la lista
        composeTestRule.onNodeWithText("Cargando cohetes...")
            .assertExists()
    }
}
