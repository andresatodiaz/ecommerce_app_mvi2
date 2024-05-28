package com.example.ecommmerceapp.presentation.Login.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.domain.Entities.Usuario
import com.example.ecommmerceapp.data.repository.UsuarioRepositoryImpl
import com.example.ecommmerceapp.domain.Repository.UsuarioRepository
import com.example.ecommmerceapp.presentation.Login.Intent.LoginContract
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
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
): ViewModel(), LoginContract {
    private val mutableState = MutableStateFlow(LoginContract.State())
    override val state: StateFlow<LoginContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<LoginContract.Effect>()
    override val effect: SharedFlow<LoginContract.Effect> =
        effectFlow.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: LoginContract.Event) = when(event){
        is LoginContract.Event.Login ->
            loginContract(event.correo,event.contrasena,event.goToMain)
        is LoginContract.Event.Register ->
            registerContract(event.correo,event.contrasena,event.nombres,event.apellidos)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loginContract(correo: String, contrasena: String, goToMain: () -> Unit){
        viewModelScope.launch {
            val startTime = LocalTime.now()
            usuarioRepository.login(correo,contrasena,goToMain)
            Log.i("comp-ExecutionTime-Login", Duration.between(startTime,LocalTime.now()).toMillis().toString())
            Log.i("comp-MemoryConsump-Login", MemoryConsumption().getUsedMemorySize().toString())

        }
    }

    private fun registerContract(correo: String,contrasena: String,nombres: String,apellidos: String){
        viewModelScope.launch {
            usuarioRepository.register(correo,contrasena,nombres,apellidos)
        }
    }
}