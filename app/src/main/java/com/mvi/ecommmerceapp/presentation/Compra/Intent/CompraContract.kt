package com.mvi.ecommmerceapp.presentation.Compra.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Entities.Usuario

interface CompraContract:
    UnidirectionalViewModel<CompraContract.State, CompraContract.Event, CompraContract.Effect> {
        data class State(
            val vendedor: Usuario = Usuario()
        )

        sealed class Event{
            data class  onGetVendedor(val id:String): Event()
            data class onComprarProducto(val producto: Producto): Event()
        }

        sealed class Effect{

        }
}