package com.pmdm.casino.data.room

import android.content.Context
import androidx.room.*
import com.pmdm.casino.data.room.usuario.UsuarioDao
import com.pmdm.casino.data.room.usuario.UsuarioEntity

@Database(
    entities = [UsuarioEntity::class],
    exportSchema = false,
    version = 1
)
abstract class CasinoDb : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var db: CasinoDb? = null

        fun getDatabase(context: Context) = Room.databaseBuilder(
            context,
            CasinoDb::class.java, "casino"
        )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    }
}
