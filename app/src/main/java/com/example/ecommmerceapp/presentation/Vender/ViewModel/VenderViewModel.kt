package com.example.ecommmerceapp.presentation.Vender.ViewModel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Service.ProductoService
import com.example.ecommmerceapp.data.repository.ProductoRepository
import com.example.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.example.ecommmerceapp.presentation.Vender.Intent.VenderContract
import com.example.ecommmerceapp.utils.MemoryConsumption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class VenderViewModel @Inject constructor(
    private val productoRepository: ProductoRepository
): ViewModel(), VenderContract {

    private val mutableState = MutableStateFlow(VenderContract.State())
    override val state: StateFlow<VenderContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<VenderContract.Effect>()
    override val effect: SharedFlow<VenderContract.Effect> =
        effectFlow.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: VenderContract.Event) = when(event) {
        is VenderContract.Event.Vender ->
            venderProductoContract(event.titulo, event.descripcion, event.precio , event.estado)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun venderProductoContract(titulo:String, descripcion: String, precio: String, estado: Int){
        viewModelScope.launch {
            val startTime = LocalTime.now()
            productoRepository.agregarProducto(titulo,descripcion,precio,estado)
            Log.i("comp-ExecutionTime-Vender", Duration.between(startTime, LocalTime.now()).toMillis().toString())
            Log.i("comp-MemoryConsump-Vender", MemoryConsumption().getUsedMemorySize().toString())
        }
    }
}