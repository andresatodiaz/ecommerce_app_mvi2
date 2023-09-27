package com.example.ecommmerceapp.data.Service

import android.util.Log
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.data.Interface.UserClient

class UserService {

    suspend fun showUsers(){
        try{
            val userResponse = UserClient.instance.getUsers()
            Log.i("resp",userResponse.toString())
        }catch (e:Exception){
            Log.e("Error de showUsers",e.toString())
        }
    }

    suspend fun login(correo:String, contrasena:String):Usuario?{
        try{
            val userResponse = UserClient.instance.login(correo, contrasena)
            if(userResponse!=null){
                return userResponse
            }else{
                return null
            }
        }catch (e:Exception){
            Log.e("Error logging",e.toString())
            return null
        }
    }

    suspend fun agregarUsuario(usuario: Usuario){
        try{
            UserClient.instance.agregarUsuario(usuario)
        }catch (e:Exception){
            Log.e("Error agregarUsuario",e.toString())
        }
    }

    suspend fun miUsuario(id:String):Usuario?{
        try{
            if(UserClient.instance.myUser(id)!=null){
                return UserClient.instance.myUser(id)
            }else{
                return null
            }
        }catch (e:Exception){
            Log.e("Error encontrando usuario",e.message.toString())
            return null
        }
    }

}