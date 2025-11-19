package com.jovicumon.spaceapps.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jovicumon.spaceapps.ui.splash.SplashScreen
import com.jovicumon.spaceapps.ui.login.LoginScreen
import com.jovicumon.spaceapps.ui.rockets.RocketsListScreen
import com.jovicumon.spaceapps.ui.rockets.RocketDetailScreen

// Rutas de la app
sealed class AppScreen(val route: String) {
    object Splash : AppScreen("splash")
    object Login : AppScreen("login")
    object RocketsList : AppScreen("rockets_list")
    object RocketDetail : AppScreen("rocket_detail")
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Splash.route
    ) {
        composable(AppScreen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(AppScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppScreen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreen.RocketsList.route) {
                        popUpTo(AppScreen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppScreen.RocketsList.route) {
            RocketsListScreen(
                onLogout = {
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(AppScreen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onRocketClick = { rocket ->
                    // Navego al detalle pasando el id del cohete
                    navController.navigate(
                        "${AppScreen.RocketDetail.route}/${rocket.id}"
                    )
                }
            )
        }

        composable(
            route = "${AppScreen.RocketDetail.route}/{rocketId}",
            arguments = listOf(
                navArgument("rocketId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rocketId = backStackEntry.arguments?.getString("rocketId") ?: ""

            RocketDetailScreen(
                rocketId = rocketId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
