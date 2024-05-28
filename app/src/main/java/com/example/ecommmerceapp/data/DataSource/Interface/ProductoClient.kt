package com.example.ecommmerceapp.data.DataSource.Interface

import com.example.ecommmerceapp.domain.Entities.Producto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductoClient {
    @GET("getProductos")
    suspend fun getProductos(): List<Producto>

    @GET("misProductos")
    suspend fun misProductos(
        @Query("id") id : Long
    ): List<Producto>

    @POST("agregarProducto")
    suspend fun agregarProducto(
        @Body producto: Producto
    )

    @POST("comprarProducto")
    suspend fun comprarProducto(
        @Body producto: Producto
    )

    @POST("borrarProducto")
    suspend fun borrarProducto(
        @Body producto: Producto
    )

    companion object {
        //private val BASE_URL = "http://10.0.2.2:9999/"
        private val BASE_URL ="https://ecommerceapprestapi-production.up.railway.app"
        val instance: ProductoClient by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductoClient::class.java)
        }
    }
}