package com.mvi.ecommmerceapp.presentation.Login.components

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
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.presentation.Login.Intent.LoginContract
import com.mvi.ecommmerceapp.presentation.Login.ViewModel.LoginViewModel
import com.mvi.ecommmerceapp.ui.theme.complementaryBrown
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    correo: MutableState<String>,
    contrasena: MutableState<String>,
    nombres:MutableState<String>,
    apellidos: MutableState<String>,
    loginViewModel: LoginViewModel,
    loginMenu: MutableState<Int>
) {
    val coroutine= rememberCoroutineScope()
    val (state, event, effect) = use(viewModel = loginViewModel)

    Column(
        modifier=Modifier.padding(20.dp)
    ) {
        Text("Nombres")
        OutlinedTextField(
            value = nombres.value,
            onValueChange = {nombres.value=it},
            modifier= Modifier.fillMaxWidth(),
            shape= CircleShape
        )
        Spacer(Modifier.padding(10.dp))
        Text("Apellidos")
        OutlinedTextField(
            value = apellidos.value,
            onValueChange = {apellidos.value=it},
            modifier= Modifier.fillMaxWidth(),
            shape= CircleShape
        )
        Spacer(Modifier.padding(10.dp))
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
                        event.invoke(
                            LoginContract.Event.Register(
                                correo.value,
                                contrasena.value,
                                nombres.value,
                                apellidos.value
                            )
                        )
                        loginMenu.value=0
                    }
                },
                modifier= Modifier.fillMaxWidth(),
                colors=ButtonDefaults.buttonColors(
                    containerColor = complementaryBrown,
                    contentColor = Color.Black
                )
            ) {
                Text("Registrarme")
            }
            Button(onClick = {
                loginMenu.value=0
                correo.value=""
                contrasena.value=""
                nombres.value=""
                apellidos.value=""
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text("Volver")
            }
        }
    }
}