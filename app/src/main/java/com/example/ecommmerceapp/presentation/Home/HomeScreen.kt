package com.example.ecommmerceapp.presentation.Home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.R
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    selectedProducto:MutableState<Producto>,
    selectedProductoUrl:MutableState<String>,
    homeViewModel: HomeViewModel,
    perfilViewModel: PerfilViewModel
) {
    val id = remember{mutableStateOf("")}
    val productoSelected = remember{ mutableStateOf(0) }
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing = homeViewModel.isLoading.value)
    LaunchedEffect(key1 = true){
        if(homeViewModel.productos.value.isEmpty()){
            homeViewModel.getProductos()
        }
        if(perfilViewModel.myUser.value.nombre.isEmpty()){
            perfilViewModel.getMyUser()
        }
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
        id.value=sp.getString("LOGGED_ID","")!!
    }

    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("crearProducto")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "vender")
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ){
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    homeViewModel.getProductos()
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2) ,
                    contentPadding = PaddingValues(top=10.dp,start=10.dp,end=10.dp)
                ){
                    item(
                        span={GridItemSpan(2)}
                    ){
                        Card(modifier= Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .height(170.dp)) {
                            Box(modifier=Modifier.fillMaxSize()){
                                AsyncImage(model = "https://picsum.photos/id/${250}/200/200/?blur=5", contentDescription = "background",
                                    modifier= Modifier
                                        .fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                                )
                                Column(
                                    modifier=Modifier.padding(20.dp)
                                ) {
                                    Text("Bienvenido a ",color=Color.White)
                                    Text(buildAnnotatedString {
                                        withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)){
                                            append("Antiq")
                                        }
                                        append("store")
                                    },
                                        color= Color.White,
                                        fontSize = 30.sp
                                    )
                                }
                            }
                        }
                    }
                    if(homeViewModel.productos.value.isEmpty()){
                        item{
                            Text("No hay productos...", modifier = Modifier.padding(top=10.dp,start=10.dp))
                        }
                    }else{
                        homeViewModel.productos.value.forEachIndexed { index, producto ->
                            item{
                                Card(
                                    modifier=Modifier.padding(5.dp)
                                        .clickable {
                                            selectedProducto.value=producto
                                            selectedProductoUrl.value="https://picsum.photos/id/${index}/200/200/?blur=2"
                                            navController.navigate("producto")
                                        }
                                ) {
                                    Box(modifier=Modifier.fillMaxSize()){
                                        AsyncImage(model = "https://picsum.photos/id/${index}/200/200/?blur=2", contentDescription = "background",
                                            modifier= Modifier
                                                .fillMaxWidth()
                                                .height(80.dp),
                                            contentScale = ContentScale.Crop,
                                            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                                        )
                                        Column(modifier=Modifier.padding(top=90.dp)) {
                                            Column(
                                                modifier=Modifier.padding(10.dp)
                                            ) {
                                                Text(producto.titulo, fontWeight = FontWeight.Black)
                                                Text(producto.descripcion, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                                Row(
                                                    modifier=Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ){
                                                    Text(producto.precio+" $", fontWeight = FontWeight.Bold,modifier=Modifier.padding(top=10.dp))
                                                    Button(
                                                        colors = ButtonDefaults.buttonColors(
                                                            containerColor = if(producto.compradoPor==id.value){
                                                                Color.Black
                                                            }else{
                                                                Color.White
                                                            }
                                                        ),
                                                        onClick = {
                                                            homeViewModel.comprarProducto(producto)
                                                            homeViewModel.getProductos()
                                                        }
                                                    ) {
                                                        Icon(painter = painterResource(id = R.drawable.buy),"",modifier=Modifier.size(20.dp),
                                                            tint=if(producto.compradoPor==id.value){
                                                                Color.White
                                                            }else{
                                                                Color.Black
                                                            }
                                                        )
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
            }

        }
    }
}