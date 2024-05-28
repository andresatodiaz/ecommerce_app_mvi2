package com.example.ecommmerceapp.presentation.Home.Intent

import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import com.example.ecommmerceapp.domain.Entities.Producto
import com.example.ecommmerceapp.domain.Entities.Usuario

interface HomeContract:
    UnidirectionalViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>{
    data class State(
        val productos:List<Producto> = listOf(),
        val refreshing: Boolean = true,
        val usuario: Usuario = Usuario()
    )

    sealed class Event {
        object onGetProductos : Event()
        object onRefresh: Event()
    }

    sealed class Effect{
        object onBackPressed: Effect()
    }

}