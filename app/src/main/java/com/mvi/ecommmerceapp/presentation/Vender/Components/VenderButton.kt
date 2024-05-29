package com.mvi.ecommmerceapp.presentation.Vender.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.UDF.StateDispatchEffect
import com.mvi.ecommmerceapp.presentation.Vender.Intent.VenderContract
import com.mvi.ecommmerceapp.ui.theme.complementaryBrown
import kotlinx.coroutines.launch

@Composable
fun VenderButton(
    titulo:MutableState<String>,
    descripcion:MutableState<String>,
    precio:MutableState<String>,
    estado:MutableState<String>,
    navController:NavController,
    venderEvent: StateDispatchEffect<VenderContract.State, VenderContract.Event, VenderContract.Effect>
) {
    val coroutine = rememberCoroutineScope()
    Row(
        modifier= Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.End
    ){
        Button(
            onClick = {
                coroutine.launch {
                    venderEvent.dispatch.invoke(
                        VenderContract.Event.Vender(titulo.value, descripcion.value, precio.value, estado.value.toInt())
                    )
                    titulo.value=""
                    descripcion.value=""
                    precio.value=""
                    estado.value=""
                    navController.navigate("home")
                }
            },
            modifier= Modifier.width(150.dp),
            colors= ButtonDefaults.buttonColors(
                containerColor = complementaryBrown,
                contentColor = Color.Black
            )
        ) {
            Text("Vender",modifier= Modifier.padding(5.dp))
            Icon(imageVector = Icons.Default.Check, contentDescription = "check" )
        }
    }
}