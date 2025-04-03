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
        startDestination = SplashRoute
    ) {
        splashDestination(
            onNavegarLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            onNavegarJuegos = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo") {
                    popUpTo("casino/$correo/$saldo") { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        loginDestination(
            onNavegarNuevaCuenta = {
                navController.navigate(NuevoUsuarioRoute)
            },

            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo") {
                    popUpTo("casino/$correo/$saldo") { inclusive = false }
                    launchSingleTop = true
                }
            }
        )

        nuevoUsuarioDestination (
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )

        casinoDestination(
            onNavegarBlackJack = { correo, saldo ->
                navController.navigate("black_jack/$correo/$saldo") {
                    launchSingleTop = true
                }
            },
            onNavegarTragaMonedas = { correo, saldo ->
                navController.navigate("traga_monedas/$correo/$saldo") {
                    launchSingleTop = true
                }
            },
            onNavegarRuleta = { correo, saldo ->
                navController.navigate("ruleta/$correo/$saldo") {
                    launchSingleTop = true
                }
            }
        )

        blackDestination(
            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo")
            }
        )

        ruletaDestination(
            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo")
            }
        )

        tragaDestination(
            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo")
            }
        )
    }
}