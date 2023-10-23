package com.example.ecommmerceapp.presentation.Producto.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.repository.ProductoRepository
import com.example.ecommmerceapp.data.repository.UsuarioRepository
import com.example.ecommmerceapp.presentation.Perfil.Intent.PerfilContract
import com.example.ecommmerceapp.presentation.Producto.Intent.ProductoContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository
): ViewModel(), ProductoContract {
    private val mutableState = MutableStateFlow(ProductoContract.State())
    override val state: StateFlow<ProductoContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<ProductoContract.Effect>()
    override val effect: SharedFlow<ProductoContract.Effect> =
        effectFlow.asSharedFlow()

    override fun event(event: ProductoContract.Event)= when(event) {
        is ProductoContract.Event.onEliminarProducto->
            eliminar(event.producto)
        is ProductoContract.Event.onDeshacerCompra->
            deshacer(event.producto)
        is ProductoContract.Event.onGetData->
            getData(event.id)
    }

    private fun eliminar(producto:Producto){
        viewModelScope.launch {
            productoRepository.borrarProducto(producto)
        }
    }
    private fun deshacer(producto: Producto){
        viewModelScope.launch {
            productoRepository.comprarProducto(producto)
        }
    }

    private fun getData(id:String){
        viewModelScope.launch {
            val vendedor=usuarioRepository.getVendedor(id)!!
            val usuario = usuarioRepository.getMyUser()!!
            mutableState.update {
                ProductoContract.State(
                    vendedor=vendedor,
                    usuario=usuario
                )
            }
        }
    }



}