package com.mvi.ecommmerceapp.presentation.Login.Intent

import com.mvi.ecommmerceapp.UDF.UnidirectionalViewModel
import com.mvi.ecommmerceapp.domain.Entities.Usuario
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.mvi.ecommmerceapp.presentation.Perfil.Intent.PerfilContract

interface LoginContract:
    UnidirectionalViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>{
        data class State(
            val loggedUser: Usuario = Usuario(),
            val refreshing: Boolean = false
        )

        sealed class Event{

            data class Login(val correo:String, val contrasena:String,val goToMain: () -> Unit): Event()

            data class Register(val correo:String, val contrasena:String, val nombres: String, val apellidos: String): Event()
        }

        sealed class Effect{
            object onBackPressed: LoginContract.Effect()
        }
}