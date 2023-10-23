package com.example.ecommmerceapp.presentation.Perfil.ViewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.data.Service.UsuarioService
import com.example.ecommmerceapp.data.repository.ProductoRepository
import com.example.ecommmerceapp.data.repository.UsuarioRepository
import com.example.ecommmerceapp.presentation.Perfil.Intent.PerfilContract
import com.example.ecommmerceapp.presentation.Vender.Intent.VenderContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository
):ViewModel(), PerfilContract {
    val myUser = mutableStateOf(Usuario())

    private val mutableState = MutableStateFlow(PerfilContract.State())
    override val state: StateFlow<PerfilContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<PerfilContract.Effect>()
    override val effect: SharedFlow<PerfilContract.Effect> =
        effectFlow.asSharedFlow()

    val refreshing = mutableStateOf(true)

    override fun event(event: PerfilContract.Event)= when(event) {
        is PerfilContract.Event.getPerfil->
            getData(isRefreshing = true)
        is PerfilContract.Event.onRefresh-> getData(isRefreshing = true)
    }

    private fun getData(
        isRefreshing:Boolean=false
    ){
        refreshing.value=true
        viewModelScope.launch {
            val usuario = usuarioRepository.getMyUser()
            val productos = productoRepository.getMisProductos(usuario.id)
            mutableState.update {
                PerfilContract.State(
                    usuario=usuario,
                    productos = productos!!,
                )
            }
            refreshing.value=false
        }
    }


    fun testUser(){
        myUser.value=Usuario(
            id="c2816877-a027-4527-852c-20c2d33e2895",
            nombre="test1",
            apellido="test1",
            correo= "demo@gmail.com",
            contrasena ="123"
        )
    }
}