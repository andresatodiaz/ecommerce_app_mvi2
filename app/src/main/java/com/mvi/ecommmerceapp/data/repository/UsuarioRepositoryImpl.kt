package com.mvi.ecommmerceapp.data.repository

import android.content.Context
import android.util.Log
import com.mvi.ecommmerceapp.MainApplication
import com.mvi.ecommmerceapp.domain.Entities.Usuario
import com.mvi.ecommmerceapp.data.Service.UsuarioService
import com.mvi.ecommmerceapp.domain.Repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val usuarioService: UsuarioService
): UsuarioRepository {
    override suspend fun getVendedor(id:String): Usuario?{
        Log.i("vendedor",id)
        return if(usuarioService.getVendedor(id)==null){
            Usuario()
        }else{
            usuarioService.getVendedor(id)
        }
    }

    override suspend fun login(correo:String, contrasena:String, goToMain:()->Unit){
        val loggedUser = usuarioService.login(correo,contrasena)!!
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        ).edit()
        sp.putString("LOGGED_ID",loggedUser.id)
        sp.apply()
        goToMain()
    }

    override suspend fun register(correo: String, contrasena: String, nombres: String, apellidos:String){
        usuarioService.agregarUsuario(
            Usuario(
                "",
                nombres,
                apellidos,
                null,
                correo,
                contrasena
            )
        )
    }

    override suspend fun getMyUser(): Usuario {
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
        return if(usuarioService.miUsuario(sp.getString("LOGGED_ID","")!!) != null && sp.getString("LOGGED_ID","")!=""){
            usuarioService.miUsuario(sp.getString("LOGGED_ID","")!!)!!
        }else{
            Usuario()
        }
    }
}