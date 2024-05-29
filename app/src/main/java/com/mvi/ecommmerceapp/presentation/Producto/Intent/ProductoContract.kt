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
             data class OnDeshacerCompra(val producto: Producto): Event()
             data class OnEliminarProducto(val producto: Producto): Event()
             data class OnGetData(val id:String):Event()
         }
        sealed class Effect{
            object onBackPressed: ProductoContract.Effect()
        }
}