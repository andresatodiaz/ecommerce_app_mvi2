package com.example.ecommmerceapp.presentation.Home.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Service.ProductoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoService: ProductoService
): ViewModel() {
    val productos = mutableStateOf(emptyList<Producto>())
    val isLoading = mutableStateOf(false)

    fun getProductos(){
        viewModelScope.launch {
            isLoading.value=true
           if( productoService.getProductos() !=null){
               productos.value= productoService.getProductos()!!.sortedBy {producto->
                   producto.precio.toInt()
               }
           }
            isLoading.value=false
        }
    }

    fun comprarProducto(producto: Producto){
        viewModelScope.launch {
            val sp = MainApplication.applicationContext().getSharedPreferences(
                "preferences",
                Context.MODE_PRIVATE
            )

            productoService.comprarProducto(
                Producto(
                    producto.id,
                    producto.titulo,
                    producto.descripcion,
                    producto.precio,
                    producto.estado,
                    sp.getString("LOGGED_ID",""),
                    producto.vendidoPor
                )
            )
        }
    }

    fun borrarProducto(producto:Producto){
        viewModelScope.launch {
            productoService.borrarProducto(producto)
            getProductos()
        }
    }
}