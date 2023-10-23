package com.example.ecommmerceapp.data.repository

import android.content.Context
import android.util.Log
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.data.Service.UsuarioService

class UsuarioRepository(
    private val usuarioService: UsuarioService
) {
    suspend fun getVendedor(id:String):Usuario?{
        Log.i("vendedor",id)
        if(usuarioService.getVendedor(id)==null){
            return Usuario()
        }else{
            Log.i("vendedor",usuarioService.getVendedor(id).toString())
            return usuarioService.getVendedor(id)
        }
    }

    suspend fun login(correo:String,contrasena:String,goToMain:()->Unit){
        val loggedUser = usuarioService.login(correo,contrasena)!!
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        ).edit()
        sp.putString("LOGGED_ID",loggedUser.id)
        sp.apply()
        goToMain()
    }

    suspend fun register(correo: String, contrasena: String, nombres: String, apellidos:String){
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

    suspend fun getMyUser():Usuario{
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
        if(usuarioService.miUsuario(sp.getString("LOGGED_ID","")!!) != null){
            return usuarioService.miUsuario(sp.getString("LOGGED_ID","")!!)!!
        }else{
            return Usuario()
        }
    }
}