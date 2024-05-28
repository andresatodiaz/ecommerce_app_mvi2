package com.example.ecommmerceapp.presentation.Perfil.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.domain.Entities.Usuario
import com.example.ecommmerceapp.data.repository.ProductoRepositoryImpl
import com.example.ecommmerceapp.data.repository.UsuarioRepositoryImpl
import com.example.ecommmerceapp.presentation.Perfil.Intent.PerfilContract
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
class PerfilViewModel @Inject constructor(
    private val productoRepository: ProductoRepositoryImpl,
    private val usuarioRepository: UsuarioRepositoryImpl
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
            getData()
        is PerfilContract.Event.onRefresh->
            getData()
    }

    private fun getData(){
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
}