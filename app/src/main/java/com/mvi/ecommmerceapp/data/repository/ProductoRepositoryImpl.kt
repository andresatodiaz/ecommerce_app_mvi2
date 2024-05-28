package com.mvi.ecommmerceapp.data.repository

import android.content.Context
import android.util.Log
import com.mvi.ecommmerceapp.MainApplication
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.data.Service.ProductoService
import com.mvi.ecommmerceapp.domain.Repository.ProductoRepository

class ProductoRepositoryImpl(
    private val productoService: ProductoService
): ProductoRepository {
    override suspend fun getProductos(): List<Producto>? {
        if(productoService.getProductos().isNullOrEmpty()){
            return emptyList()
        }else{
            Log.i("productosRep", productoService.getProductos().toString())
            return productoService.getProductos()?.sortedBy { producto->
                producto.precio.toInt()
            }
        }
    }

    override suspend fun getMisProductos(id: String): List<Producto> {
        if(productoService.getProductos().isNullOrEmpty()){
            return emptyList()
        }else{
            return productoService.getProductos()!!.filter {producto->
                producto.vendidoPor==id
            }
        }
    }

    override suspend fun comprarProducto(producto: Producto){
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

    override suspend fun borrarProducto(producto: Producto){
        productoService.borrarProducto(producto)
    }

    override suspend fun agregarProducto(titulo:String, descripcion:String, precio:String, estado:Int){
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