package com.mvi.ecommmerceapp.presentation.Vender.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.mvi.ecommmerceapp.presentation.Perfil.Intent.PerfilContract

interface VenderContract:
    UnidirectionalViewModel<VenderContract.State, VenderContract.Event, VenderContract.Effect> {
        data class State(
            val refresh: Boolean=false
        )

        sealed class Event {
            data class Vender(
                val titulo:String,
                val descripcion:String,
                val precio:String,
                val estado:Int)
                : Event()
        }

        sealed class Effect{
            object onBackPressed: VenderContract.Effect()
        }
}