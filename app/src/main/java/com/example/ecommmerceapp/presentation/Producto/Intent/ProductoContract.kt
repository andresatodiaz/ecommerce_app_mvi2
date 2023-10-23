package com.example.ecommmerceapp.presentation.Producto.Intent

import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Entities.Usuario

interface ProductoContract:
    UnidirectionalViewModel<ProductoContract.State, ProductoContract.Event, ProductoContract.Effect>{
        data class  State(
            val vendedor: Usuario= Usuario(),
            val usuario: Usuario= Usuario()
        )
         sealed class Event{
             data class onDeshacerCompra(val producto: Producto): Event()
             data class onEliminarProducto(val producto: Producto): Event()
             data class onGetData(val id:String):Event()
         }
        sealed class Effect{

        }
}