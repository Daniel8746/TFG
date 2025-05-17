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
import com.pmdm.casino.ui.features.ruleta.RuletaViewModel
import com.pmdm.casino.ui.features.tragaMonedas.TragaMonedasViewModel
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel

@Composable
fun CasinoNavHost(
    vmApuestas: ApuestasViewModel
) {
    val navController: NavHostController = rememberNavController()
    var esLogin by remember { mutableStateOf(true) }

    val vmBlackJ = hiltViewModel<BlackJackViewModel>()
    val vmMaquina = hiltViewModel<MaquinaViewModel>()
    val vmRuleta = hiltViewModel<RuletaViewModel>()
    val vmTragaM = hiltViewModel<TragaMonedasViewModel>()
    val vmUsuarioC = hiltViewModel<UsuarioCasinoViewModel>()

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
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
            onNavegarJuegos = {
                navController.navigate(CasinoRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }

                esLogin = false
            },
            vmUsuarioCasino = vmUsuarioC
        )

        loginDestination(
            onNavegarNuevaCuenta = {
                navController.navigate(NuevoUsuarioRoute)
            },
            onNavegarCasino = {
                navController.navigate(CasinoRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }

                esLogin = false
            },
            vmUsuarioCasino = vmUsuarioC
        )

        nuevoUsuarioDestination(
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )

        casinoDestination(
            onNavegarBlackJack = {
                navController.navigate(BlackJackRoute)
            },
            onNavegarTragaMonedas = {
                navController.navigate(TragaMonedasRoute)
            },
            onNavegarRuleta = {
                navController.navigate(RuletaRoute)
            },
            vmUsuarioCasino = vmUsuarioC
        )

        blackDestination(
            onNavegarCasino = {
                navController.popBackStack()
            },
            vm = vmBlackJ,
            vmMaquina = vmMaquina,
            vmApuestas = vmApuestas,
            vmUsuarioCasino = vmUsuarioC
        )

        ruletaDestination(
            onNavegarCasino = {
                navController.popBackStack()
            },
            vm = vmRuleta,
            vmUsuarioCasino = vmUsuarioC
        )

        tragaDestination(
            onNavegarCasino = {
                navController.popBackStack()
            },
            vm = vmTragaM,
            vmUsuarioCasino = vmUsuarioC
        )
    }
}