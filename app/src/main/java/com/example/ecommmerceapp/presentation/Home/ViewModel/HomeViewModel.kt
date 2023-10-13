package com.example.ecommmerceapp.presentation.Home.ViewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.data.Service.ProductoService
import com.example.ecommmerceapp.data.Service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoService: ProductoService,
    private val userService: UserService
): ViewModel() {
    val productos = mutableStateOf(emptyList<Producto>())
    val isLoading = mutableStateOf(false)
    val vendedorProducto = mutableStateOf(Usuario())
    val loadingVendedor= mutableStateOf(false)
    val vendedorEmpty = mutableStateOf(false)
    val misProductos= mutableStateOf(emptyList<Producto>())

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

    fun getMisProductos(id:String){
        viewModelScope.launch {
            isLoading.value=true
            if( productoService.getProductos() !=null){
                misProductos.value= productoService.getProductos()!!.filter {producto->
                    producto.vendidoPor==id
                }
            }
            Log.i("home",misProductos.value.toString())
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

    fun getVendedor(id:String){
        viewModelScope.launch {
            if(userService.getVendedor(id)!=null){
                loadingVendedor.value=true
                if(userService.getVendedor(id)==null){
                    vendedorEmpty.value=true
                }else{
                    vendedorProducto.value=userService.getVendedor(id)!!
                }
                loadingVendedor.value=false
            }
        }
    }
}