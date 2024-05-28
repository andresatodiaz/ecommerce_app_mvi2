package com.example.ecommmerceapp.presentation.Compra.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.domain.Entities.Producto
import com.example.ecommmerceapp.data.repository.ProductoRepositoryImpl
import com.example.ecommmerceapp.data.repository.UsuarioRepositoryImpl
import com.example.ecommmerceapp.domain.Repository.ProductoRepository
import com.example.ecommmerceapp.domain.Repository.UsuarioRepository
import com.example.ecommmerceapp.presentation.Compra.Intent.CompraContract
import com.example.ecommmerceapp.utils.MemoryConsumption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val productoRepository: ProductoRepository
): ViewModel(), CompraContract{

    private val mutableState = MutableStateFlow(CompraContract.State())
    override val state: StateFlow<CompraContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<CompraContract.Effect>()
    override val effect: SharedFlow<CompraContract.Effect> =
        effectFlow.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: CompraContract.Event)= when(event) {
        is CompraContract.Event.onComprarProducto->
            comprar(event.producto)
        is CompraContract.Event.onGetVendedor->
            getVendedor(event.id)
    }

    private fun getVendedor(id:String){
        viewModelScope.launch {
            val vendedor = usuarioRepository.getVendedor(id)!!
            mutableState.update {
                CompraContract.State(
                    vendedor=vendedor
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun comprar(producto: Producto){
        viewModelScope.launch{
            val startTime = LocalTime.now()
            productoRepository.comprarProducto(producto)
            Log.i("comp-ExecutionTime-Compra", Duration.between(startTime, LocalTime.now()).toMillis().toString())
            Log.i("comp-MemoryConsump-Compra", MemoryConsumption().getUsedMemorySize().toString())
        }
    }




}