package com.mvi.ecommmerceapp.data.Service

import android.util.Log
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.data.DataSource.Interface.ProductoClient

class ProductoService {

    suspend fun getProductos():List<Producto>?{
        try{
            val productos = ProductoClient.instance.getProductos()
            return if(productos.isNotEmpty()){
                productos
            }else{
                emptyList()
            }
        }catch (e:Exception){
            return emptyList()
        }
    }

    suspend fun misProductos(id: Long):List<Producto>{
        try {
            val misProductos = ProductoClient.instance.misProductos(id)
            return if(misProductos.isNotEmpty()){
                misProductos
            }else{
                emptyList()
            }
        }catch (e:Exception){
            return emptyList()
        }
    }

    suspend fun agregarProductos(producto: Producto){
        try{
            ProductoClient.instance.agregarProducto(producto)
        }catch (e:Exception){
            Log.e("AgregarProducto",e.message.toString())
        }
    }

    suspend fun comprarProducto(producto: Producto){
        try{
            ProductoClient.instance.comprarProducto(producto)
        }catch (e:Exception){
            Log.e("ComprarProducto",e.message.toString())
        }
    }

    suspend fun borrarProducto(producto: Producto){
        try{
            ProductoClient.instance.borrarProducto(producto)
        }catch (e:Exception){
            Log.e("BorrarProducto",e.message.toString())
        }
    }

}