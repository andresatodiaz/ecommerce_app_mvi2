package com.example.ecommmerceapp.domain.Entities

data class Producto(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val estado: Int = 0,
    val compradoPor: String?="",
    val vendidoPor: String?=""
)