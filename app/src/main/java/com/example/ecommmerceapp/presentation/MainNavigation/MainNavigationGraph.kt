package com.example.ecommmerceapp.presentation.MainNavigation

import androidx.activity.compose.BackHandler
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.presentation.Compra.CompraScreen
import com.example.ecommmerceapp.presentation.Vender.CrearProductoScreen
import com.example.ecommmerceapp.presentation.Vender.ViewModel.VenderViewModel
import com.example.ecommmerceapp.presentation.Discover.DiscoverScreen
import com.example.ecommmerceapp.presentation.Home.HomeScreen
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.Perfil.PerfilScreen
import com.example.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.example.ecommmerceapp.presentation.Producto.ProductoScreen
import com.example.ecommmerceapp.presentation.QrScanner.QrScannerScreen
import com.example.ecommmerceapp.presentation.QrScanner.ViewModel.QrScannerViewModel
import com.example.ecommmerceapp.presentation.Signature.DigitalInkViewModel
import com.example.ecommmerceapp.presentation.Signature.DigitalInkViewModelImpl

@ExperimentalGetImage
@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    perfilViewModel: PerfilViewModel = hiltViewModel(),
    venderViewModel: VenderViewModel = hiltViewModel(),
    showQRScanner: MutableState<Boolean>,
    qrScannerViewModel: QrScannerViewModel= hiltViewModel(),
    digitalInkViewModel: DigitalInkViewModel = hiltViewModel<DigitalInkViewModelImpl>()
) {
    val selectedProducto = remember{mutableStateOf(Producto())}
    val selectedProductoUrl = remember{ mutableStateOf("") }
    NavHost(
        navController = navController,
        startDestination = "home",

        ) {
        composable("home"){
            qrScannerViewModel.qrLink.value=""
            HomeScreen(navController,selectedProducto,selectedProductoUrl,homeViewModel,perfilViewModel)
        }
        composable("discover"){
            DiscoverScreen(navController,homeViewModel)
        }
        composable("profile"){
            PerfilScreen(
                finishActivity,
                flagKillActivity,
                perfilViewModel,
                homeViewModel,
                navController,selectedProducto,selectedProductoUrl
            )
        }
        composable("vender"){
            CrearProductoScreen(navController,
                venderViewModel,
                homeViewModel
            )
        }
        composable("producto"){
            qrScannerViewModel.qrLink.value=""
            BackHandler {
                navController.navigate("home")
            }
            ProductoScreen(
                photo = selectedProductoUrl.value,
                producto = selectedProducto.value,
                navController=navController,
                homeViewModel=homeViewModel,
                showQRScanner=showQRScanner,
                perfilViewModel = perfilViewModel
            )
        }
        composable("compra"){
            BackHandler {
                navController.navigate("home")
            }
            CompraScreen(
                photo = selectedProductoUrl.value,
                producto = selectedProducto.value,
                navController=navController,
                homeViewModel=homeViewModel,
                qrScannerViewModel=qrScannerViewModel,
                digitalInkViewModel=digitalInkViewModel
            )

        }
        composable("qrscanner"){
            BackHandler {
                navController.navigate("producto")
            }
            QrScannerScreen(navController,qrScannerViewModel)
        }
    }

}