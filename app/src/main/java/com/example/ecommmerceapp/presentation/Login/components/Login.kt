package com.example.ecommmerceapp.presentation.Login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ecommmerceapp.presentation.Login.ViewModel.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    correo: MutableState<String>,
    contrasena: MutableState<String>,
    loginViewModel:LoginViewModel,
    loginMenu: MutableState<Int>,
    goToMain: ()-> Unit
) {
    val coroutine= rememberCoroutineScope()

    Column() {
        Text("Correo")
        OutlinedTextField(
            value = correo.value,
            onValueChange = {correo.value=it},
            modifier= Modifier.fillMaxWidth(),
            shape= CircleShape
        )
        Spacer(Modifier.padding(10.dp))
        Text("Contraseña")
        OutlinedTextField(
            value = contrasena.value,
            onValueChange = {contrasena.value=it},
            modifier= Modifier.fillMaxWidth(),
            shape= CircleShape
        )
        Spacer(Modifier.padding(10.dp))
        Column(
            modifier= Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = {
                    coroutine.launch {
                        loginViewModel.login(correo.value,contrasena.value){
                            goToMain()
                        }
                    }
                },
                modifier= Modifier.fillMaxWidth()
            ) {
                Text("Log In")
            }
            Button(onClick = {
                loginMenu.value=1
                correo.value=""
                contrasena.value=""
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(buildAnnotatedString {
                    withStyle(style= SpanStyle(fontWeight = FontWeight.Normal)){
                        append("No tiene usuario? ")
                    }
                    withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)){
                        append("Cree uno")
                    }
                })
            }
        }
    }
}