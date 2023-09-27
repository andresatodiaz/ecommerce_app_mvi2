package com.example.ecommmerceapp.presentation.Perfil.ViewModel

import android.content.Context
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
class PerfilViewModel @Inject constructor(
    private val userService: UserService
):ViewModel() {
    val myUser = mutableStateOf(Usuario())

    fun getMyUser(){
        viewModelScope.launch {
            val sp = MainApplication.applicationContext().getSharedPreferences(
                "preferences",
                Context.MODE_PRIVATE
            )
            if(userService.miUsuario(sp.getString("LOGGED_ID","")!!) != null){
                myUser.value= userService.miUsuario(sp.getString("LOGGED_ID","")!!)!!
            }

        }
    }
}