package com.example.ecommmerceapp.presentation.Discover

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.R
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiscoverScreen(
    navController:NavController,
    homeViewModel: HomeViewModel
){
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing = false)
    LaunchedEffect(key1 = true){
        if (homeViewModel.productos.value.isEmpty()){
            homeViewModel.getProductos()
        }
    }

    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )

    Scaffold(
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
                LazyVerticalGrid(columns = GridCells.Fixed(2) ){
                    homeViewModel.productos.value.forEachIndexed { index, producto ->
                        item{
                            Card(modifier= Modifier.padding(5.dp)) {
                                Box(modifier= Modifier.fillMaxSize()){
                                    AsyncImage(model = "https://picsum.photos/id/${index}/200/200/?blur=2", contentDescription = "background",
                                        modifier= Modifier
                                            .fillMaxWidth()
                                            .height(80.dp),
                                        contentScale = ContentScale.Crop,
                                        colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                                    )
                                    Column(modifier= Modifier.padding(top=90.dp)) {
                                        Column(
                                            modifier= Modifier.padding(10.dp)
                                        ) {
                                            Text(producto.titulo, fontWeight = FontWeight.Black)
                                            Text(producto.descripcion, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                            Row(
                                                modifier= Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ){
                                                Text(producto.precio+" $", fontWeight = FontWeight.Bold,modifier= Modifier.padding(top=10.dp))

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