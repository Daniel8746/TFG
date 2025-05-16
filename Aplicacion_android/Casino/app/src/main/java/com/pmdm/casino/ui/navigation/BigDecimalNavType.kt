package com.pmdm.casino.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import java.math.BigDecimal

@Deprecated(
    message = "Se ha centralizado toda la lógica del usuario a un viewmodel para que sea menos complejo"
)
class BigDecimalNavType : NavType<BigDecimal>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): BigDecimal? {
        return bundle.getString(key)?.let { BigDecimal(it) }
    }

    override fun put(bundle: Bundle, key: String, value: BigDecimal) {
        bundle.putString(key, value.toString())
    }

    override fun parseValue(value: String): BigDecimal {
        return BigDecimal(value)
    }
}