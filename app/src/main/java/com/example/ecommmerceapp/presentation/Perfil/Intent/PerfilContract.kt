package com.example.ecommmerceapp.presentation.Perfil.Intent

import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.presentation.Home.Intent.HomeContract

interface PerfilContract:
    UnidirectionalViewModel<PerfilContract.State, PerfilContract.Event, PerfilContract.Effect> {
        data class State(
            val usuario:Usuario= Usuario(),
            val refreshing:Boolean=true
        )

        sealed class Event {
            object getPerfil : Event()
            object onRefresh : Event()
        }

        sealed class Effect{

        }
}