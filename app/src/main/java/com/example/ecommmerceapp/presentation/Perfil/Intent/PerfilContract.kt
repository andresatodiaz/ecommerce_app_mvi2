package com.example.ecommmerceapp.presentation.Perfil.Intent

import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import com.example.ecommmerceapp.domain.Entities.Producto
import com.example.ecommmerceapp.domain.Entities.Usuario

interface PerfilContract:
    UnidirectionalViewModel<PerfilContract.State, PerfilContract.Event, PerfilContract.Effect> {
        data class State(
            val usuario: Usuario = Usuario(),
            val productos: List<Producto> = emptyList(),
            val refreshing:Boolean=true
        )

        sealed class Event {
            object getPerfil : Event()

            object onRefresh : Event()
        }

        sealed class Effect{

        }
}