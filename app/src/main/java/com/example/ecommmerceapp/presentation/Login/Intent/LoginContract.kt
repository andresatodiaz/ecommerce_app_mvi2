package com.example.ecommmerceapp.presentation.Login.Intent

import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import com.example.ecommmerceapp.data.Entities.Usuario

interface LoginContract:
    UnidirectionalViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>{
        data class State(
            val loggedUser: Usuario = Usuario(),
            val refresh: Boolean = false
        )

        sealed class Event{

            data class Login(val correo:String, val contrasena:String,val goToMain: () -> Unit): Event()

            data class Register(val correo:String, val contrasena:String, val nombres: String, val apellidos: String): Event()
        }

        sealed class Effect{

        }
}