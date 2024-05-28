package com.example.ecommmerceapp.domain.Repository

import com.example.ecommmerceapp.domain.Entities.Usuario

interface UsuarioRepository {
    suspend fun getVendedor(id:String): Usuario?
    suspend fun login(correo:String,contrasena:String,goToMain:()->Unit)
    suspend fun register(correo: String, contrasena: String, nombres: String, apellidos:String)
    suspend fun getMyUser(): Usuario
}