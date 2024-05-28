package com.example.ecommmerceapp.presentation.Compra.Intent

import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import com.example.ecommmerceapp.domain.Entities.Producto
import com.example.ecommmerceapp.domain.Entities.Usuario
import com.example.ecommmerceapp.presentation.Home.Intent.HomeContract

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