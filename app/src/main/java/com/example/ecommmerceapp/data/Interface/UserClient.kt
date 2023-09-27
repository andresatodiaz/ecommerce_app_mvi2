package com.example.ecommmerceapp.data.Interface

import com.example.ecommmerceapp.data.Entities.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UserClient {

    @GET("getUsuarios")
    suspend fun getUsers(): List<Usuario>

    @GET("miUsuario")
    suspend fun myUser(
        @Query("id") id:String
    ): Usuario

    @GET("login")
    suspend fun login(
        @Query("correo") correo:String,
        @Query("contrasena") contrasena:String
    ): Usuario?

    @POST("addUsuario")
    suspend fun agregarUsuario(
        @Body usuario: Usuario
    )


    companion object {
        //private val BASE_URL = "http://10.0.2.2:9999/"
        private val BASE_URL ="https://ecommerceapprestapi-production.up.railway.app"

        val instance: UserClient by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserClient::class.java)
        }
    }
}