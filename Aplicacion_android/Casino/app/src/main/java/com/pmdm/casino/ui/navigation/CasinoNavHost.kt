package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun CasinoNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        loginDestination(
            onNavegarNuevaCuenta = {
                navController.navigate(NuevoUsuarioRoute)
            },

            onNavegarCasino = {
                navController.navigate(CasinoRoute(it)) {
                    popUpTo(CasinoRoute) { inclusive = false }
                }
            }
        )

        nuevoUsuarioDestination (
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )

        casinoDestination()
    }
}