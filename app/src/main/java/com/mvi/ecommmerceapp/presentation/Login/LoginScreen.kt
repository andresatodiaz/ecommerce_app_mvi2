package com.mvi.ecommmerceapp.presentation.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mvi.ecommmerceapp.presentation.Login.ViewModel.LoginViewModel
import com.mvi.ecommmerceapp.presentation.Login.components.Login
import com.mvi.ecommmerceapp.presentation.Login.components.LoginBanner
import com.mvi.ecommmerceapp.presentation.Login.components.Register

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    goToMain:()-> Unit
) {
    val focusManager = LocalFocusManager.current

    val correo = remember{ mutableStateOf("") }
    val contrasena = remember{ mutableStateOf("") }
    val nombres = remember{ mutableStateOf("")}
    val apellidos = remember{ mutableStateOf("")}
    val loginMenu = remember{ mutableStateOf(0) }

    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(0xffeeeeee))
            .clickable { focusManager.clearFocus() }
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth(0.8f)
                .background(Color.White, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center)
        ) {
            LoginBanner()
            Spacer(Modifier.padding(10.dp))
            if(loginMenu.value==0){
                Login(
                    correo = correo,
                    contrasena = contrasena,
                    loginViewModel = loginViewModel,
                    loginMenu = loginMenu,
                    goToMain = goToMain
                )
            }else{
                Register(
                    correo = correo,
                    contrasena = contrasena,
                    nombres=nombres,
                    apellidos=apellidos,
                    loginViewModel = loginViewModel,
                    loginMenu = loginMenu
                )
            }

        }
    }
}