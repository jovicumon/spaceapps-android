package com.jovicumon.spaceapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jovicumon.spaceapps.ui.navigation.AppNavHost
import com.jovicumon.spaceapps.ui.theme.SpaceAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Punto de entrada de SpaceApps: aquí engancho el tema y la navegación.
        setContent {
            SpaceAppsTheme {
                SpaceAppsApp()
            }
        }
    }
}

@Composable
fun SpaceAppsApp() {
    val navController = rememberNavController()

    // De momento uso una Surface sencilla. Si luego necesito un Scaffold general, lo meteré aquí.
    Surface(color = MaterialTheme.colorScheme.background) {
        AppNavHost(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SpaceAppsPreview() {
    SpaceAppsTheme {
        SpaceAppsApp()
    }
}
