package com.example.ecommmerceapp.presentation.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommmerceapp.data.Service.UserService
import com.example.ecommmerceapp.presentation.Login.ViewModel.LoginViewModel
import com.example.ecommmerceapp.presentation.Login.components.Login
import com.example.ecommmerceapp.presentation.Login.components.Register
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    goToMain:()-> Unit
) {
    val correo = remember{ mutableStateOf("") }
    val contrasena = remember{ mutableStateOf("") }

    val nombres = remember{ mutableStateOf("")}
    val apellidos = remember{ mutableStateOf("")}


    val coroutine= rememberCoroutineScope()
    val loginMenu = remember{ mutableStateOf(0) }

    LaunchedEffect(key1 = true){
        loginViewModel.showUsers()
    }

    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(0xffeeeeee))
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth(0.8f)
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(20.dp)
                .align(Alignment.Center)
        ) {
            Text(buildAnnotatedString {
                withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Antiq")
                }
                append("store")
            })
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