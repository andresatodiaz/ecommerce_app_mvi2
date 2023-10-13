package com.example.ecommmerceapp.presentation.Perfil

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.LoginActivity
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.example.ecommmerceapp.ui.theme.cardBrown
import com.example.ecommmerceapp.ui.theme.complementaryBrown
import com.example.ecommmerceapp.ui.theme.mainBrown
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    perfilViewModel: PerfilViewModel,
    homeViewModel: HomeViewModel,
    navController:NavController,
    selectedProducto:MutableState<Producto>,
    selectedProductoUrl:MutableState<String>,
) {
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing = homeViewModel.isLoading.value)
    val context= LocalContext.current
    val coroutine = rememberCoroutineScope()
    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    LaunchedEffect(key1 = true){
        if(perfilViewModel.myUser.value.nombre.isEmpty()){
            perfilViewModel.getMyUser()
        }
        Log.i("nombre",perfilViewModel.myUser.value.nombre.toString())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(model = "https://picsum.photos/id/${200}/200/200/?blur=5", contentDescription = "background",
            modifier= Modifier
                .fillMaxWidth()
                .height(230.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Row(
            modifier= Modifier
            .padding(top = 150.dp, start = 15.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "person",tint=Color.White,modifier=Modifier.padding(end=10.dp))
            Text(perfilViewModel.myUser.value.nombre.capitalize()+" "+perfilViewModel.myUser.value.apellido.capitalize(),
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontSize = 30.sp
            )
        }
        Button(
            shape= CircleShape,
            onClick = {
                coroutine.launch {
                    val editor = MainApplication.applicationContext().getSharedPreferences(
                        "preferences",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("LOGGED_ID","")
                    editor.apply()
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    finishActivity
                    flagKillActivity.value=true
                }
            },
            modifier= Modifier
                .padding(end = 20.dp, top = 20.dp)
                .align(Alignment.TopEnd),
            colors= ButtonDefaults.buttonColors(
                containerColor = complementaryBrown,
                contentColor = Color.Black
            )
        ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "log out",modifier=Modifier.size(20.dp))
        }
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                homeViewModel.getMisProductos(perfilViewModel.myUser.value.id)
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    fade = true,
                    contentColor = Color.LightGray,
                    scale = true,
                    backgroundColor = Color.White,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 100))
                )
            }
        ) {
            LazyColumn(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 270.dp, bottom = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item{
                    Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                        Text("Correo", fontWeight = FontWeight.Bold)
                        Text(perfilViewModel.myUser.value.correo)
                    }
                    Spacer(Modifier.padding(10.dp))
                }
                item{
                    Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                        Text("Contrasena", fontWeight = FontWeight.Bold)
                        Text(perfilViewModel.myUser.value.contrasena)
                    }
                    Spacer(Modifier.padding(10.dp))
                }
                item{
                    Text("Mis productos", fontWeight = FontWeight.Bold,modifier=Modifier.fillMaxWidth(0.9f))
                    if(homeViewModel.misProductos.value.isEmpty()){
                        Text("No hay productos",modifier= Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.9f),color=Color.Gray)
                    }
                    Spacer(Modifier.padding(10.dp))
                }
                itemsIndexed(homeViewModel.misProductos.value){index,producto->
                    Card(
                        modifier= Modifier
                            .padding(5.dp, 5.dp, 5.dp, 10.dp)
                            .clickable {
                                selectedProducto.value = producto
                                selectedProductoUrl.value =
                                    "https://picsum.photos/id/${index}/200/200/?blur=2"
                                navController.navigate("producto")
                            }
                    ) {
                        Box(
                            modifier= Modifier
                                .fillMaxSize(0.9f)
                                .background(cardBrown)
                        ){
                            AsyncImage(model = "https://picsum.photos/id/${index}/200/200/?blur=2", contentDescription = "background",
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                            )
                            Column(
                                modifier=Modifier.padding(top=90.dp)
                            ) {
                                Column(
                                    modifier= Modifier
                                        .background(cardBrown)
                                        .padding(10.dp)
                                ) {
                                    Text(producto.titulo, fontWeight = FontWeight.Black)
                                    Text(producto.descripcion, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                    Row(
                                        modifier=Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ){
                                        Text(producto.precio+" $", fontWeight = FontWeight.Bold,modifier=Modifier.padding(top=10.dp))
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }
    }
}