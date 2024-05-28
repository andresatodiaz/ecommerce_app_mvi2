package com.mvi.ecommmerceapp.domain.Repository

import com.mvi.ecommmerceapp.domain.Entities.Producto

interface ProductoRepository {
    suspend fun getProductos(): List<Producto>?
    suspend fun getMisProductos(id: String): List<Producto>
    suspend fun comprarProducto(producto: Producto)
    suspend fun borrarProducto(producto: Producto)
    suspend fun agregarProducto(titulo:String,descripcion:String,precio:String,estado:Int)
}