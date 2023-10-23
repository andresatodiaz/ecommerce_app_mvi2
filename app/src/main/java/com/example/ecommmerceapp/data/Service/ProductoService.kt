package com.example.ecommmerceapp.data.Service

import android.util.Log
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Interface.ProductoClient
import kotlin.Exception

class ProductoService {

    suspend fun getProductos():List<Producto>?{
        try{
            val productos = ProductoClient.instance.getProductos()
            if(productos.isNotEmpty()){
                return productos
            }else{
                return emptyList()
            }
        }catch (e:Exception){
            return emptyList()
        }
    }

    suspend fun misProductos(id: Long):List<Producto>{
        try {
            val misProductos = ProductoClient.instance.misProductos(id)
            if(misProductos.isNotEmpty()){
                return misProductos
            }else{
                return emptyList()
            }
        }catch (e:Exception){
            return emptyList()
        }
    }

    suspend fun agregarProductos(producto: Producto){
        try{
            ProductoClient.instance.agregarProducto(producto)
        }catch (e:Exception){
            Log.e("Error agregando producto",e.message.toString())
        }
    }

    suspend fun comprarProducto(producto: Producto){
        try{
            ProductoClient.instance.comprarProducto(producto)
        }catch (e:Exception){

        }
    }

    suspend fun borrarProducto(producto: Producto){
        try{
            ProductoClient.instance.borrarProducto(producto)
        }catch (e:Exception){

        }
    }

}