package com.pmdm.casino.data.room.usuario

import androidx.room.*

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(contacto : UsuarioEntity)

    @Delete
    suspend fun delete(contacto : UsuarioEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(contacto : UsuarioEntity)
}