package com.example.ecommmerceapp.presentation.Producto

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.R
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.example.ecommmerceapp.ui.theme.cardBrown
import com.example.ecommmerceapp.ui.theme.complementaryBrown
import com.example.ecommmerceapp.ui.theme.priceColor
import com.example.ecommmerceapp.ui.theme.secondaryBrown
import kotlinx.coroutines.launch

@Composable
fun ProductoScreen(
    photo: String,
    producto:Producto,
    navController: NavController,
    homeViewModel: HomeViewModel,
    perfilViewModel: PerfilViewModel,
    showQRScanner:MutableState<Boolean>
) {
    LaunchedEffect(key1 = true){
        homeViewModel.vendedorProducto.value= Usuario()
        homeViewModel.getVendedor(producto.vendidoPor!!)
    }

    Log.i("photo",photo)
    val coroutine = rememberCoroutineScope()
    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    val id = remember{ mutableStateOf("") }
    LaunchedEffect(key1 = true){
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
        id.value=sp.getString("LOGGED_ID","")!!
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(model = photo, contentDescription = "background",
            modifier= Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Text(producto.titulo,modifier= Modifier.padding(top=80.dp), fontWeight = FontWeight.Black, color = Color.White, fontSize = 20.sp)
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Row(){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "descripcion",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Descripción", fontWeight = FontWeight.Bold)
                    }
                    Text(producto.descripcion)
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Row(){
                        Icon(painter = painterResource(id = R.drawable.price), contentDescription = "price",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Precio", fontWeight = FontWeight.Bold)
                    }
                    Text(producto.precio+" $",modifier= Modifier
                        .background(
                            priceColor,
                            CircleShape
                        )
                        .padding(10.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Row(){
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "estado",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Estado", fontWeight = FontWeight.Bold)
                    }
                    Text(producto.estado.toString()+"/10",
                        modifier= Modifier
                            .background(
                                cardBrown,
                                CircleShape
                            )
                            .padding(10.dp),
                        fontWeight = FontWeight.Bold
                        )

                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Vendido por", fontWeight = FontWeight.Bold, modifier=Modifier.padding(bottom=10.dp))
                    Column(
                        modifier= Modifier
                            .background(cardBrown, RoundedCornerShape(20.dp))
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        if(homeViewModel.loadingVendedor.value || homeViewModel.vendedorProducto.value.id==""){
                            Text("Cargando vendedor")
                        }else{
                            if(!homeViewModel.vendedorEmpty.value){
                                Text(homeViewModel.vendedorProducto.value.nombre.capitalize()+" "+homeViewModel.vendedorProducto.value.apellido.capitalize())
                                Text(homeViewModel.vendedorProducto.value.correo, fontWeight = FontWeight.Bold)
                            }else{
                                Text("Usuario no disponible")
                            }
                        }
                    }
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(
                    modifier=Modifier.fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.End
                ){
                    Column(modifier=Modifier.width(150.dp)){
                        Button(
                            enabled = producto.vendidoPor!=perfilViewModel.myUser.value.id,
                            onClick = {
                                if(producto.compradoPor!=perfilViewModel.myUser.value.id){
                                    navController.navigate("compra")
                                }else{
                                    homeViewModel.comprarProducto(producto)
                                    homeViewModel.getProductos()
                                    navController.navigate("home")
                                }

                            },
                            modifier= Modifier.fillMaxWidth(),
                            colors= ButtonDefaults.buttonColors(
                                containerColor = complementaryBrown,
                                contentColor = Color.Black
                            )
                        ) {
                            if(producto.compradoPor!=perfilViewModel.myUser.value.id){
                                Text("Comprar")
                            }else{
                                Text("Cancelar compra")
                            }
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "compra",modifier=Modifier.padding(5.dp))
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        if(producto.vendidoPor==perfilViewModel.myUser.value.id){
                            Button(
                                onClick = {
                                    coroutine.launch {
                                        homeViewModel.borrarProducto(producto)
                                        navController.navigate("home")
                                        homeViewModel.getProductos()
                                        homeViewModel.getMisProductos(perfilViewModel.myUser.value.id)
                                    }
                                },
                                modifier= Modifier.fillMaxWidth(),
                                colors= ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Borrar")
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete",modifier=Modifier.padding(5.dp))
                            }
                        }
                    }
                }

            }

        }
    }
}