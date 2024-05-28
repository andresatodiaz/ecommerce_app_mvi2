package com.mvi.ecommmerceapp.presentation.Vender.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel

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

        }
}