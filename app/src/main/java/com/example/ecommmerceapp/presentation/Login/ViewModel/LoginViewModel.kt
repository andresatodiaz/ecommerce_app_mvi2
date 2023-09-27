package com.example.ecommmerceapp.presentation.Login.ViewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.data.Service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService
): ViewModel() {
    val loggedUser= mutableStateOf(Usuario())
    val loginSuccess = mutableStateOf(false)
    val loginLoading = mutableStateOf(true)
    fun showUsers(){
        viewModelScope.launch {
            userService.showUsers()
        }
    }

    fun login(correo: String, contrasena: String,goToMain: ()->Unit){
        viewModelScope.launch {
            loginLoading.value=true
            if(userService.login(correo,contrasena)!=null){
                loggedUser.value= userService.login(correo,contrasena)!!
                Log.i("loginSuccess","true")
                Log.i("loggedUser",loggedUser.value.toString())
                val sp = MainApplication.applicationContext().getSharedPreferences(
                    "preferences",
                    Context.MODE_PRIVATE
                ).edit()
                sp.putString("LOGGED_ID",loggedUser.value.id.toString())
                goToMain()
                sp.apply()
                loginLoading.value=false
                loginSuccess.value=true
            }else{
                loginLoading.value=false
                loginSuccess.value=false
            }

        }
    }

    fun agregarUsuario(correo: String, contrasena: String, nombres: String, apellidos:String){
        viewModelScope.launch {
            userService.agregarUsuario(
                Usuario(
                    "",
                    nombres,
                    apellidos,
                    null,
                    correo,
                    contrasena
                )
            )
        }
    }
}