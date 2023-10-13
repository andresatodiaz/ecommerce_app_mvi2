package com.example.ecommmerceapp.presentation.Vender.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Service.ProductoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenderViewModel @Inject constructor(
    private val productoService: ProductoService
): ViewModel() {
    fun agregarProducto(titulo:String,descripcion:String,precio:String,estado:Int){
        viewModelScope.launch {
            val sp = MainApplication.applicationContext().getSharedPreferences(
                "preferences",
                Context.MODE_PRIVATE
            )

            productoService.agregarProductos(
                Producto(
                "",
                    titulo,
                    descripcion,
                    precio,
                    estado,
                    null,
                    sp.getString("LOGGED_ID","")
                )
            )
        }
    }
}