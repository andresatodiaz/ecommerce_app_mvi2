package com.mvi.ecommmerceapp.presentation.Discover.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.ecommmerceapp.domain.Repository.ProductoRepository
import com.mvi.ecommmerceapp.domain.Repository.UsuarioRepository
import com.mvi.ecommmerceapp.presentation.Discover.Intent.DiscoverContract
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.mvi.ecommmerceapp.utils.MemoryConsumption
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
class DiscoverViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val productoRepository: ProductoRepository
): ViewModel(), DiscoverContract{
    private val mutableState = MutableStateFlow(DiscoverContract.State())
    override val state: StateFlow<DiscoverContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<DiscoverContract.Effect>()
    override val effect: SharedFlow<DiscoverContract.Effect> =
        effectFlow.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: DiscoverContract.Event){
        when(event){
            is DiscoverContract.Event.onGetProductos->
                getData()
            DiscoverContract.Event.onRefresh -> getData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(
    ){
        viewModelScope.launch {
            mutableState.update {
                mutableState.value.copy(refreshing = true)
            }
            val startTime = LocalTime.now()
            val usuario = usuarioRepository.getMyUser()
            val productos = productoRepository.getProductos()!!

            mutableState.update {
                DiscoverContract.State(
                    usuario = usuario,
                    productos= productos,
                    refreshing = false
                )
            }
            Log.i("comp-ExecutionTime-Home", Duration.between(startTime,LocalTime.now()).toMillis().toString())
            Log.i("comp-MemoryConsump-Home", MemoryConsumption().getUsedMemorySize().toString())
        }

    }
}