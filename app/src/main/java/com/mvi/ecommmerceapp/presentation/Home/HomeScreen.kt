package com.mvi.ecommmerceapp.presentation.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.mvi.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mvi.ecommmerceapp.presentation.Home.Components.Grid
import com.mvi.ecommmerceapp.presentation.Home.Components.HomeFloatingButton
import com.mvi.ecommmerceapp.presentation.Home.Components.HomeSwipeIndicator

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    selectedProducto:MutableState<Producto>,
    selectedProductoUrl:MutableState<String>,
    homeViewModel: HomeViewModel,
) {
    val id = remember{mutableStateOf("")}
    val (state, event) = use(viewModel = homeViewModel)
    val refreshing=homeViewModel.refreshing.value
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing =refreshing)

    LaunchedEffect(key1 = true){
        event.invoke(
            HomeContract.Event.onGetProductos
        )
    }

    LaunchedEffect(key1 = state.loggedId){
        id.value=state.loggedId
    }


    Scaffold(
        floatingActionButton = {
            HomeFloatingButton(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ){
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    event.invoke(HomeContract.Event.onGetProductos)
                },
                indicator = { state, trigger ->
                    HomeSwipeIndicator(state,trigger)
                }
            ) {
                Grid(
                    usuario = state.usuario,
                    refreshing = state.refreshing,
                    productos = state.productos,
                    id=id.value,
                    navController=navController,
                    selectedProducto=selectedProducto,
                    selectedProductoUrl=selectedProductoUrl,
                )
            }

        }
    }
}