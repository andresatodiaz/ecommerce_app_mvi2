package com.mvi.ecommmerceapp.presentation.Home.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Entities.Usuario

interface HomeContract:
    UnidirectionalViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>{
    data class State(
        val productos:List<Producto> = listOf(),
        val refreshing: Boolean = true,
        val usuario: Usuario = Usuario(),
        val loggedId:String=""
    )

    sealed class Event {
        object onGetProductos : Event()
        object onRefresh: Event()
    }

    sealed class Effect{
        object onBackPressed: Effect()
    }

}