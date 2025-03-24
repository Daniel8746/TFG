package com.pmdm.casino.ui.features.nuevousuario

import com.github.pmdmiesbalmis.components.validacion.Validador
import com.github.pmdmiesbalmis.components.validacion.ValidadorCompuesto
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorCorreo
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorLongitudMinimaTexto
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorTelefono
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorNuevoUsuario @Inject constructor() : Validador<NuevoUsuarioUiState> {
    var validadorNombre =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El nombre no puede estar vacío"))

    var validadorApellidos =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("Los apellidos no puede estar vacío"))

    var validadorCorreo =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El correo no puede estar vacío"))
            .add(ValidadorCorreo("El correo no es correcto"))

    var validadorPassword =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El password no puede estar vacío"))
            .add(ValidadorLongitudMinimaTexto(8, "El password debe tener como mínimo 8 carácteres"))

    var validadorTelefono =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El teléfono no puede estar vacío"))
            .add(ValidadorTelefono("El teléfono no es correcto"))

    override fun valida(datos: NuevoUsuarioUiState): ValidacionNuevoUsuarioUiState {
        val validacionNombre = validadorNombre.valida(datos.nombre)
        val validacionApellidos = validadorApellidos.valida(datos.apellidos)
        val validacionCorreo = validadorCorreo.valida(datos.correo)
        val validacionPassword = validadorPassword.valida(datos.password)
        val validacionTelefono = validadorTelefono.valida(datos.telefono)

        return ValidacionNuevoUsuarioUiState(
            validacionNombre = validacionNombre,
            validacionApellidos = validacionApellidos,
            validacionCorreo = validacionCorreo,
            validacionPassword = validacionPassword,
            validacionTelefono = validacionTelefono
        )
    }
}