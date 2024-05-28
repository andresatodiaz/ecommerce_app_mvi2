package com.mvi.ecommmerceapp.presentation.Producto.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Entities.Usuario

interface ProductoContract:
    UnidirectionalViewModel<ProductoContract.State, ProductoContract.Event, ProductoContract.Effect>{
        data class  State(
            val vendedor: Usuario = Usuario(),
            val usuario: Usuario = Usuario()
        )
         sealed class Event{
             data class onDeshacerCompra(val producto: Producto): Event()
             data class onEliminarProducto(val producto: Producto): Event()
             data class onGetData(val id:String):Event()
         }
        sealed class Effect{

        }
}