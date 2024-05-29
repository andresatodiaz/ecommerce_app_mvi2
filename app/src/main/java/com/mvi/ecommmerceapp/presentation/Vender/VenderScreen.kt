package com.mvi.ecommmerceapp.presentation.Vender

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.presentation.Vender.Components.VenderBanner
import com.mvi.ecommmerceapp.presentation.Vender.Components.VenderButton
import com.mvi.ecommmerceapp.presentation.Vender.Components.VenderDescripcion
import com.mvi.ecommmerceapp.presentation.Vender.Components.VenderEstado
import com.mvi.ecommmerceapp.presentation.Vender.Components.VenderPrecio
import com.mvi.ecommmerceapp.presentation.Vender.Components.VenderTitulo
import com.mvi.ecommmerceapp.presentation.Vender.ViewModel.VenderViewModel
@Composable
fun CrearProductoScreen(
    navController: NavController,
    venderViewModel: VenderViewModel,
) {
    val titulo = remember{ mutableStateOf("") }
    val descripcion = remember{ mutableStateOf("") }
    val precio = remember{ mutableStateOf("") }
    val estado = remember{ mutableStateOf("1") }
    val tablero = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
    var expanded = remember { mutableStateOf(false) }
    val event = use(venderViewModel)


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        VenderBanner()
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                VenderTitulo(titulo = titulo)
            }
            item{
                VenderDescripcion(descripcion = descripcion)
            }
            item{
                VenderPrecio(precio = precio)
            }
            item{
                VenderEstado(estado = estado, expanded = expanded, tablero =tablero )
            }
            item{
                VenderButton(
                    titulo=titulo,
                    descripcion=descripcion,
                    estado=estado,
                    precio = precio,
                    venderEvent = event,
                    navController = navController
                )

            }

        }
    }
}