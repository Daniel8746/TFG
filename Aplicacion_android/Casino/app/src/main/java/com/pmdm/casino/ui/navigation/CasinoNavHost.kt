package com.pmdm.casino.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pmdm.casino.ui.features.apuestas.ApuestasViewModel
import com.pmdm.casino.ui.features.blackJack.BlackJackViewModel
import com.pmdm.casino.ui.features.blackJack.MaquinaViewModel
import com.pmdm.casino.ui.features.casino.JuegosViewModel
import com.pmdm.casino.ui.features.ruleta.RuletaViewModel
import com.pmdm.casino.ui.features.tragaMonedas.TragaMonedasViewModel

@Composable
fun CasinoNavHost() {
    val navController: NavHostController = rememberNavController()
    var esLogin by remember { mutableStateOf(true) }

    val vmBJ = hiltViewModel<BlackJackViewModel>()
    val vmMaquina = hiltViewModel<MaquinaViewModel>()
    val vmApuestas = hiltViewModel<ApuestasViewModel>()
    val vmC = hiltViewModel<JuegosViewModel>()
    val vmR = hiltViewModel<RuletaViewModel>()
    val vmT = hiltViewModel<TragaMonedasViewModel>()

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        enterTransition = {
            if (esLogin) {
                // Desvanecer + deslizamiento
                fadeIn(animationSpec = tween(600, easing = FastOutSlowInEasing)) +
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        )
            } else {
                // Zoom in (acercamiento)
                scaleIn(
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        },
        exitTransition = {
            if (esLogin) {
                // Desvanecer + deslizamiento
                fadeOut(animationSpec = tween(600, easing = FastOutSlowInEasing)) +
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        )
            } else {
                // Zoom out (alejamiento)
                scaleOut(
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        },
        popEnterTransition = {
            if (esLogin) {
                // Desvanecer + deslizamiento
                fadeIn(animationSpec = tween(600, easing = FastOutSlowInEasing)) +
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        )
            } else {
                // Zoom in (acercamiento)
                scaleIn(
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        },
        popExitTransition = {
            if (esLogin) {
                // Desvanecer + deslizamiento
                fadeOut(animationSpec = tween(600, easing = FastOutSlowInEasing)) +
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        )
            } else {
                // Zoom out (alejamiento)
                scaleOut(
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        }
    ) {
        splashDestination(
            onNavegarLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(LoginRoute) { inclusive = false }
                }
            },
            onNavegarJuegos = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo") {
                    popUpTo(0) { inclusive = false }
                }

                esLogin = false
            }
        )

        loginDestination(
            onNavegarNuevaCuenta = {
                navController.navigate(NuevoUsuarioRoute)
            },

            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo") {
                    popUpTo(0) { inclusive = false }
                }

                esLogin = false
            }
        )

        nuevoUsuarioDestination(
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )

        casinoDestination(
            onNavegarBlackJack = { correo, saldo ->
                navController.navigate("black_jack/$correo/$saldo")
            },
            onNavegarTragaMonedas = { correo, saldo ->
                navController.navigate("traga_monedas/$correo/$saldo")
            },
            onNavegarRuleta = { correo, saldo ->
                navController.navigate("ruleta/$correo/$saldo")
            },
            vm = vmC
        )

        blackDestination(
            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo")
            },
            vm = vmBJ,
            vmMaquina = vmMaquina,
            vmApuestas = vmApuestas
        )

        ruletaDestination(
            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo")
            },
            vm = vmR
        )

        tragaDestination(
            onNavegarCasino = { correo, saldo ->
                navController.navigate("casino/$correo/$saldo")
            },
            vm = vmT
        )
    }
}