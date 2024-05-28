package com.mvi.ecommmerceapp.presentation.Discover

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.mvi.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.mvi.ecommmerceapp.ui.theme.cardBrown
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
    val focusManager = LocalFocusManager.current
    val searchName = remember{mutableStateOf("")}
    val (state, event, effect) = use(viewModel = homeViewModel)
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing = homeViewModel.refreshing.value)
    LaunchedEffect(key1 = true){
        if(state.productos.isEmpty()){
            event.invoke(
                HomeContract.Event.onGetProductos
            )
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
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    focusManager.clearFocus()
                },
            contentAlignment = Alignment.TopCenter
        ){
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    event.invoke(
                        HomeContract.Event.onGetProductos
                    )
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
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(top=10.dp,start=10.dp,end=10.dp,bottom=80.dp)
                ){
                    item(
                        span={ GridItemSpan(2) }
                    ){
                        OutlinedTextField(
                            value = searchName.value,
                            onValueChange = {searchName.value=it},
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 20.dp),
                            shape= CircleShape,
                            placeholder = {Text("Buscar..")},
                            singleLine = true,
                            leadingIcon = {
                                IconButton(
                                    modifier = Modifier.alpha(0.5f),
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "búsqueda icono",
                                        tint = Color.Gray
                                    )
                                }
                            },
                            trailingIcon = {
                                if(searchName.value != ""){
                                    IconButton(onClick = {
                                        searchName.value = ""}
                                    ){
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "close búsqueda",
                                            tint = Color.LightGray
                                        )
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = cardBrown,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    state.productos.forEachIndexed { index, producto ->
                        if(searchName.value==""
                            || (searchName.value!="" && producto.titulo.lowercase().contains(searchName.value.lowercase()))
                        ){
                            item{
                                Card(modifier= Modifier.padding(5.dp)) {
                                    Box(
                                        modifier= Modifier
                                            .fillMaxSize()
                                            .background(cardBrown)
                                    ){
                                        AsyncImage(model = "https://picsum.photos/id/${index}/200/200/?blur=2", contentDescription = "background",
                                            modifier= Modifier
                                                .fillMaxWidth()
                                                .height(80.dp),
                                            contentScale = ContentScale.Crop,
                                            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                                        )
                                        Column(modifier= Modifier.padding(top=90.dp)) {
                                            Column(
                                                modifier= Modifier
                                                    .background(cardBrown)
                                                    .padding(10.dp)
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
}