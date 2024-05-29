package com.mvi.ecommmerceapp.presentation.Producto.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Repository.UsuarioRepository
import com.mvi.ecommmerceapp.domain.Repository.ProductoRepository
import com.mvi.ecommmerceapp.presentation.Producto.Intent.ProductoContract
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
        is ProductoContract.Event.OnEliminarProducto->
            eliminar(event.producto)
        is ProductoContract.Event.OnDeshacerCompra->
            deshacer(event.producto)
        is ProductoContract.Event.OnGetData->
            getData(event.id)
    }

    fun eliminar(producto: Producto){
        viewModelScope.launch {
            productoRepository.borrarProducto(producto)
        }
    }
    fun deshacer(producto: Producto){
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