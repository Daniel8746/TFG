package com.pmdm.casino

import android.app.Application
import com.pmdm.casino.data.room.usuario.UsuarioDao
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class CasinoApplication : Application()  {
    @Inject
    lateinit var daoEntity: UsuarioDao

    override fun onCreate() {
        super.onCreate()

        runBlocking {

        }
    }
}