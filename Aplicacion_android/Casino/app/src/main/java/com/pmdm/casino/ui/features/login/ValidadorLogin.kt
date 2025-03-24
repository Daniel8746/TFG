package com.pmdm.casino.ui.features.login

import com.github.pmdmiesbalmis.components.validacion.Validador
import com.github.pmdmiesbalmis.components.validacion.ValidadorCompuesto
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorCorreo
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorLongitudMinimaTexto
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorLogin  @Inject constructor() : Validador<LoginUiState> {
    var validadorLogin =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El login no puede estar vacío"))
            .add(ValidadorCorreo("El correo no es válido"))

    var validadorPassword =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El password no puede estar vacío"))
            .add(ValidadorLongitudMinimaTexto(8, "El password debe tener como mínimo 8 carácteres"))

    override fun valida(datos: LoginUiState): ValidacionLoginUiState {
        val validacionLogin = validadorLogin.valida(datos.login)
        val validacionPassword = validadorPassword.valida(datos.password)

        return ValidacionLoginUiState(
            validacionLogin = validacionLogin,
            validacionPassword = validacionPassword
        )
    }
}