package com.example.ecommmerceapp.presentation.Vender

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.R
import com.example.ecommmerceapp.UDF.use
import com.example.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.example.ecommmerceapp.presentation.Vender.ViewModel.VenderViewModel
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.Vender.Intent.VenderContract
import com.example.ecommmerceapp.ui.theme.cardBrown
import com.example.ecommmerceapp.ui.theme.complementaryBrown
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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


    val (state,event,effect)= use(venderViewModel)

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
                    Row(){
                        Icon(imageVector = Icons.Default.Star, contentDescription = "titulo",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Título", fontWeight = FontWeight.Bold)
                    }
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
                    Row(){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "descripcion",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Descripción", fontWeight = FontWeight.Bold)
                    }
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
                    Row(){
                        Icon(painter = painterResource(id = R.drawable.price), contentDescription = "price",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Precio", fontWeight = FontWeight.Bold)
                    }
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
                    Row(){
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "estado",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Estado", fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                expanded.value = true
                            })
                            .background(
                                cardBrown,
                                CircleShape
                            )
                            .padding(
                                top = 10.dp,
                                bottom = 10.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Text(
                            text=estado.value+" /10",
                            fontWeight = FontWeight.Bold,
                            textAlign= TextAlign.Center
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "",
                            modifier=Modifier.size(20.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(
                                cardBrown
                            ),
                        offset = DpOffset(0.dp,5.dp)
                    ) {
                        tablero.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text={
                                     Text(item.toString())
                                },
                                onClick = {
                                estado.value = item.toString()
                                expanded.value = false }
                            )
                        }
                    }
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Row(
                    modifier=Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        onClick = {
                            coroutine.launch {
                                event.invoke(
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
                        colors=ButtonDefaults.buttonColors(
                            containerColor = complementaryBrown,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Vender",modifier=Modifier.padding(5.dp))
                        Icon(imageVector = Icons.Default.Check, contentDescription = "check" )
                    }
                }

            }

        }
    }
}