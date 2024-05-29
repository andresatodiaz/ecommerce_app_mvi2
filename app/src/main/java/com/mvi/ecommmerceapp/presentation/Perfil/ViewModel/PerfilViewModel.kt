package com.mvi.ecommmerceapp.presentation.Perfil.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.ecommmerceapp.domain.Repository.UsuarioRepository
import com.mvi.ecommmerceapp.presentation.Perfil.Intent.PerfilContract
import com.mvi.ecommmerceapp.domain.Repository.ProductoRepository
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
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository
):ViewModel(), PerfilContract {

    private val mutableState = MutableStateFlow(PerfilContract.State())
    override val state: StateFlow<PerfilContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<PerfilContract.Effect>()
    override val effect: SharedFlow<PerfilContract.Effect> =
        effectFlow.asSharedFlow()

    override fun event(event: PerfilContract.Event)= when(event) {
        is PerfilContract.Event.getPerfil->
            getData()
        is PerfilContract.Event.onRefresh->
            getData()
    }

    private fun getData(){
        mutableState.update {
            mutableState.value.copy(refreshing = true)
        }
        viewModelScope.launch {
            val usuario = usuarioRepository.getMyUser()
            val productos = productoRepository.getMisProductos(usuario.id)
            mutableState.update {
                PerfilContract.State(
                    usuario=usuario,
                    productos = productos,
                    refreshing = false
                )
            }
        }
    }
}