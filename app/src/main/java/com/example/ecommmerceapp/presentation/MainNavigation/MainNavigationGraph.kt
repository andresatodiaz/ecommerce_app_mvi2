package com.example.ecommmerceapp.presentation.MainNavigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.presentation.CrearProducto.CrearProductoScreen
import com.example.ecommmerceapp.presentation.CrearProducto.ViewModel.CrearProductoViewModel
import com.example.ecommmerceapp.presentation.Discover.DiscoverScreen
import com.example.ecommmerceapp.presentation.Home.HomeScreen
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.Perfil.PerfilScreen
import com.example.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.example.ecommmerceapp.presentation.Producto.ProductoScreen

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    perfilViewModel: PerfilViewModel = hiltViewModel(),
    crearProductoViewModel: CrearProductoViewModel = hiltViewModel()
) {
    val selectedProducto = remember{mutableStateOf(Producto())}
    val selectedProductoUrl = remember{ mutableStateOf("") }
    NavHost(
        navController = navController,
        startDestination = "home",

        ) {
        composable("home"){
            HomeScreen(navController,selectedProducto,selectedProductoUrl,homeViewModel,perfilViewModel)
        }
        composable("discover"){
            DiscoverScreen(navController,homeViewModel)
        }
        composable("profile"){
            PerfilScreen(
                finishActivity,
                flagKillActivity,
                perfilViewModel
            )
        }
        composable("crearProducto"){
            CrearProductoScreen(navController,
                crearProductoViewModel
            )
        }
        composable("producto"){
            ProductoScreen(
                photo = selectedProductoUrl.value,
                producto = selectedProducto.value,
                navController=navController,
                homeViewModel=homeViewModel
            )
        }
    }

}