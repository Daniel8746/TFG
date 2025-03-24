package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.nuevousuario.NuevoUsuarioScreen
import com.pmdm.casino.ui.features.nuevousuario.NuevoUsuarioViewModel
import kotlinx.serialization.Serializable

@Serializable
object NuevoUsuarioRoute

fun NavGraphBuilder.nuevoUsuarioDestination(
    onNavigateToLogin: () -> Unit
) {
    composable<NuevoUsuarioRoute> {
        val vm = hiltViewModel<NuevoUsuarioViewModel>()

        NuevoUsuarioScreen(
            nuevoUsuarioUiState = vm.nuevoUsuarioUiState,
            validacionNuevoUsuarioUiState = vm.validacionNuevoUsuarioUiState,
            nuevoUsuarioError = vm.usuarioCreado.collectAsState().value,
            isLoading = vm.isLoading.collectAsState().value,
            onNuevoUsuarioEvent = { vm.onNuevoUsuarioEvent(it) },
            onNavigateToLogin = onNavigateToLogin
        )
    }
}