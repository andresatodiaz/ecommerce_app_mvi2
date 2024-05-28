package com.example.ecommmerceapp.data.repository

import android.content.Context
import android.util.Log
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.domain.Entities.Producto
import com.example.ecommmerceapp.data.Service.ProductoService

class ProductoRepositoryImpl(
    private val productoService: ProductoService
) {
    suspend fun getProductos(): List<Producto>? {
        if(productoService.getProductos().isNullOrEmpty()){
            return emptyList()
        }else{
            Log.i("productosRep", productoService.getProductos().toString())
            return productoService.getProductos()?.sortedBy { producto->
                producto.precio.toInt()
            }
        }
    }

    suspend fun getMisProductos(id: String): List<Producto>?{
        if(productoService.getProductos().isNullOrEmpty()){
            return emptyList()
        }else{
            return productoService.getProductos()!!.filter {producto->
                producto.vendidoPor==id
            }
        }
    }

    suspend fun comprarProducto(producto: Producto){
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )

        productoService.comprarProducto(
            Producto(
                producto.id,
                producto.titulo,
                producto.descripcion,
                producto.precio,
                producto.estado,
                sp.getString("LOGGED_ID",""),
                producto.vendidoPor
            )
        )
    }

    suspend fun borrarProducto(producto: Producto){
        productoService.borrarProducto(producto)
    }

    suspend fun agregarProducto(titulo:String,descripcion:String,precio:String,estado:Int){
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )

        productoService.agregarProductos(
            Producto(
                "",
                titulo,
                descripcion,
                precio,
                estado,
                null,
                sp.getString("LOGGED_ID","")
            )
        )
    }
}