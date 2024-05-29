package com.mvi.ecommmerceapp.presentation.Discover.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Entities.Usuario
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract

interface DiscoverContract:
    UnidirectionalViewModel<DiscoverContract.State, DiscoverContract.Event, DiscoverContract.Effect> {
    data class State(
        val usuario: Usuario = Usuario(),
        val productos: List<Producto> = emptyList(),
        val refreshing: Boolean = true,
    )

    sealed class Event{
        object onGetProductos : DiscoverContract.Event()
        object onRefresh: DiscoverContract.Event()
    }

    sealed class Effect{
        object onBackPressed: DiscoverContract.Effect()
    }
}