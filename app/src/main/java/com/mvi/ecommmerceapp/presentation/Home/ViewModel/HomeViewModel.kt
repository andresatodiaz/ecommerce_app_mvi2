package com.mvi.ecommmerceapp.presentation.Home.ViewModel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.ecommmerceapp.MainApplication
import com.mvi.ecommmerceapp.domain.Repository.ProductoRepository
import com.mvi.ecommmerceapp.domain.Repository.UsuarioRepository
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
class HomeViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository
): ViewModel(), HomeContract {

    val refreshing = mutableStateOf(true)

    private val mutableState = MutableStateFlow(HomeContract.State())
    override val state: StateFlow<HomeContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<HomeContract.Effect>()
    override val effect: SharedFlow<HomeContract.Effect> =
        effectFlow.asSharedFlow()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: HomeContract.Event) = when(event){
        is HomeContract.Event.onGetProductos->
            getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(
    ){
        refreshing.value=true
        viewModelScope.launch {
            val startTime = LocalTime.now()
            val usuario = usuarioRepository.getMyUser()
            val productos = productoRepository.getProductos()!!

            mutableState.update {
                HomeContract.State(
                    usuario = usuario,
                    productos= productos,
                )
            }
            getToken()
            refreshing.value=false
            Log.i("comp-ExecutionTime-Home", Duration.between(startTime,LocalTime.now()).toMillis().toString())
            Log.i("comp-MemoryConsump-Home", MemoryConsumption().getUsedMemorySize().toString())
        }
    }

    fun getToken(){
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
        mutableState.update {
            mutableState.value.copy(
                loggedId = sp.getString("LOGGED_ID","")!!
            )
        }

    }
}