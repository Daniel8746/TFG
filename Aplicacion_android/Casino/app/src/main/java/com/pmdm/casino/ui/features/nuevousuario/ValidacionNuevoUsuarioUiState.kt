package com.pmdm.casino.ui.features.nuevousuario

import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.github.pmdmiesbalmis.components.validacion.ValidacionCompuesta

data class ValidacionNuevoUsuarioUiState(
    val validacionNombre: Validacion = object : Validacion {},
    val validacionApellidos: Validacion = object : Validacion {},
    val validacionCorreo: Validacion = object : Validacion {},
    val validacionPassword: Validacion = object : Validacion {},
    val validacionTelefono: Validacion = object  : Validacion {}
): Validacion {
    private lateinit var validacionCompuesta: ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionNombre)
            .add(validacionApellidos)
            .add(validacionCorreo)
            .add(validacionPassword)
            .add(validacionTelefono)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}