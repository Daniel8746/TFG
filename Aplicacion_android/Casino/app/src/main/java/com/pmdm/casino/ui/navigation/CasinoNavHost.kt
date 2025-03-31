package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import java.math.BigDecimal

@Composable
fun CasinoNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        splashDestination(
            onNavegarLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(LoginRoute) { inclusive = false }
                }
            },
            onNavegarJuegos = { correo, saldo ->
                navController.navigate(CasinoRoute(correo, saldo)) {
                    popUpTo(CasinoRoute) { inclusive = false }
                }
            }
        )

        loginDestination(
            onNavegarNuevaCuenta = {
                navController.navigate(NuevoUsuarioRoute)
            },

            onNavegarCasino = { correo, saldo ->
                navController.navigate(CasinoRoute(correo, saldo)) {
                    popUpTo(CasinoRoute) { inclusive = false }
                }
            }
        )

        nuevoUsuarioDestination (
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )

        casinoDestination(
            onNavegarBlackJack = { corrreo, saldo ->

            },
            onNavegarTragaMonedas = { correo, saldo ->

            },
            onNavegarRuleta = { correo, saldo ->

            }
        )
    }
}