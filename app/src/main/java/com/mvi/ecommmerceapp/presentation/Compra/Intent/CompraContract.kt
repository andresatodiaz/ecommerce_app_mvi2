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
            data class  OnGetVendedor(val id:String): Event()
            data class OnComprarProducto(val producto: Producto): Event()
        }

        sealed class Effect{
            object onBackPressed: CompraContract.Effect()
        }
}