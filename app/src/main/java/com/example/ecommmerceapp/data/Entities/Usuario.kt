package com.example.ecommmerceapp.data.Entities

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val fechaNacimiento: String? = "",
    val correo: String  = "",
    val contrasena: String  = ""
)
