package com.example.ecommmerceapp.presentation.CrearProducto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.presentation.CrearProducto.ViewModel.CrearProductoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearProductoScreen(
    navController: NavController,
    crearProductoViewModel: CrearProductoViewModel
) {
    val titulo = remember{ mutableStateOf("") }
    val descripcion = remember{ mutableStateOf("") }
    val precio = remember{ mutableStateOf("") }
    val estado = remember{ mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(model = "https://picsum.photos/500/500/?blur=5", contentDescription = "background",
            modifier= Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Text("Vender producto",modifier=Modifier.padding(top=50.dp), fontWeight = FontWeight.Black, color = Color.White, fontSize = 20.sp)
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                Column(modifier=Modifier.fillMaxWidth(0.9f)) {
                    Text("Titulo")
                    OutlinedTextField(
                        value = titulo.value,
                        onValueChange = {titulo.value=it},
                        modifier= Modifier.fillMaxWidth(),
                        shape= CircleShape
                    )
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier=Modifier.fillMaxWidth(0.9f)) {
                    Text("Descripcion")
                    OutlinedTextField(
                        value = descripcion.value,
                        onValueChange = {descripcion.value=it},
                        modifier= Modifier.fillMaxWidth(),
                        shape= CircleShape
                    )
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier=Modifier.fillMaxWidth(0.9f)) {
                    Text("Precio")
                    OutlinedTextField(
                        value = precio.value,
                        onValueChange = {precio.value=it},
                        modifier= Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        shape= CircleShape
                    )
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier=Modifier.fillMaxWidth(0.9f)) {
                    Text("Estado")
                    OutlinedTextField(
                        value = estado.value,
                        onValueChange = {estado.value=it},
                        modifier= Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        shape= CircleShape
                    )
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Button(
                    onClick = {
                        coroutine.launch {
                            crearProductoViewModel.agregarProducto(titulo.value, descripcion.value, precio.value, estado.value.toInt())
                            titulo.value=""
                            descripcion.value=""
                            precio.value=""
                            estado.value=""
                            navController.navigate("home")
                        }
                    },
                    modifier= Modifier.fillMaxWidth(0.9f)
                ) {
                    Text("Vender")
                }
            }

        }
    }
}