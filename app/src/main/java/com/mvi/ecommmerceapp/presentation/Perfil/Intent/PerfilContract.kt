package com.mvi.ecommmerceapp.presentation.Perfil.Intent

import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Entities.Usuario
import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract

interface PerfilContract:
    UnidirectionalViewModel<PerfilContract.State, PerfilContract.Event, PerfilContract.Effect> {
        data class State(
            val usuario: Usuario = Usuario(),
            val productos: List<Producto> = emptyList(),
            val refreshing:Boolean=true
        )

        sealed class Event {
            object getPerfil : Event()
        }

        sealed class Effect{
            object onBackPressed: PerfilContract.Effect()
        }
}