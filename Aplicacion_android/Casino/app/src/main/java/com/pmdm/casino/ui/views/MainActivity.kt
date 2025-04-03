package com.pmdm.casino.ui.views

import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.pmdm.casino.ui.features.musicaFondo.MusicaViewModel
import com.pmdm.casino.ui.navigation.CasinoNavHost
import com.pmdm.casino.ui.theme.CasinoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Quitar action bar
        actionBar?.hide()

        // `WindowInsetsController` gestiona la visibilidad de las barras del sistema e insets en API 30+.

        // garantiza que la modificación del `WindowInsetsController`
        // ocurra después de que la vista esté completamente inicializada.
        window.decorView.post {
            // Accede directamente al `WindowInsetsController` (API 30+) sin usar compatibilidad.
            val windowInsetsController = window.insetsController

            // Configura el comportamiento de las barras del sistema
            if (windowInsetsController != null) {
                windowInsetsController.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            }
        }

        setContent {
            CasinoTheme {
                // Musica fondo
                hiltViewModel<MusicaViewModel>()

                Surface(modifier = Modifier.fillMaxSize()) {
                    CasinoNavHost()
                }
            }
        }

        // habilita el diseño de pantalla completa, extendiendo el contenido hasta los bordes.
        enableEdgeToEdge()
    }
}
