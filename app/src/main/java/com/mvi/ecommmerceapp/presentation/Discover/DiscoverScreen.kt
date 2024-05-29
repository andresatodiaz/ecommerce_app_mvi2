package com.mvi.ecommmerceapp.presentation.Discover

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mvi.ecommmerceapp.presentation.Discover.Components.DiscoverIndicator
import com.mvi.ecommmerceapp.presentation.Discover.Components.DiscoverSearchBar
import com.mvi.ecommmerceapp.presentation.Discover.Components.ProductCard
import com.mvi.ecommmerceapp.presentation.Discover.Intent.DiscoverContract
import com.mvi.ecommmerceapp.presentation.Discover.ViewModel.DiscoverViewModel
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiscoverScreen(
    discoverViewModel: DiscoverViewModel
){
    val focusManager = LocalFocusManager.current
    val searchName = remember{mutableStateOf("")}
    val refreshing =discoverViewModel.state.collectAsState().value.refreshing
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing = refreshing)
    val productos=discoverViewModel.state.collectAsState().value.productos
    LaunchedEffect(key1 = true){
        if(productos.isEmpty()){
            discoverViewModel.event(DiscoverContract.Event.onGetProductos)
        }
    }

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
                    discoverViewModel.event(DiscoverContract.Event.onGetProductos)
                },
                indicator = { state, trigger ->
                    DiscoverIndicator(state = state, trigger = trigger )
                }
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(top=10.dp,start=10.dp,end=10.dp,bottom=80.dp)
                ){
                    item(
                        span={ GridItemSpan(2) }
                    ){
                       DiscoverSearchBar(searchName = searchName)
                    }
                    productos.forEachIndexed { index, producto ->
                        if(searchName.value==""
                            || (searchName.value!="" && producto.titulo.lowercase().contains(searchName.value.lowercase()))
                        ){
                            item{
                                ProductCard(index = index, producto = producto)
                            }
                        }
                    }
                }
            }

        }
    }
}