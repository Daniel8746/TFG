package com.pmdm.casino.data.room.usuario

import androidx.room.*

@Entity(tableName = "Usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "id_imagen")
    val idImagen: Int,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "cantidad")
    val cantidad: Int,
    @ColumnInfo(name = "unidades_cantidad")
    val unidadesCantidad: String,
    @ColumnInfo(name = "laboratorio")
    val laboratorio: String,
    @ColumnInfo(name = "stock")
    val stock: Int,
    @ColumnInfo(name = "pedidos")
    val pedidos: Int
)
