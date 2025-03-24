package com.pmdm.casino.data

import com.pmdm.casino.data.services.usuario.UsuarioApi
import com.pmdm.casino.data.services.usuario.UsuarioApiRecord
import com.pmdm.casino.model.Usuario

fun Usuario.toUsuarioApi(): UsuarioApi = UsuarioApi(
    nombre, apellidos, correo, contrasena, telefono, saldo, recordarCuenta
)

fun Usuario.toUsuarioApiRecord(): UsuarioApiRecord = UsuarioApiRecord(
    correo, contrasena, saldo
)

