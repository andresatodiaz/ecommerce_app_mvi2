package com.mvi.ecommmerceapp.presentation.Perfil
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.presentation.Perfil.Intent.PerfilContract
import com.mvi.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mvi.ecommmerceapp.presentation.Perfil.Components.LogOutButton
import com.mvi.ecommmerceapp.presentation.Perfil.Components.MyCorreo
import com.mvi.ecommmerceapp.presentation.Perfil.Components.MyPassword
import com.mvi.ecommmerceapp.presentation.Perfil.Components.MyProduct
import com.mvi.ecommmerceapp.presentation.Perfil.Components.NoProducts
import com.mvi.ecommmerceapp.presentation.Perfil.Components.ProfileBanner

@Composable
fun PerfilScreen(
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    perfilViewModel: PerfilViewModel,
    navController:NavController,
    selectedProducto:MutableState<Producto>,
    selectedProductoUrl:MutableState<String>,
) {
    val (state, event) = use(viewModel = perfilViewModel)
    val refreshing=state.refreshing
    val swipeRefreshState  = rememberSwipeRefreshState(isRefreshing =refreshing )
    val productos=state.productos

    LaunchedEffect(key1 = true){
        if(state.usuario.id==""){
            event.invoke(
                PerfilContract.Event.getPerfil
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        ProfileBanner(usuario = state.usuario)
        LogOutButton(
            finishActivity = finishActivity,
            flagKillActivity=flagKillActivity,
            modifier=Modifier.align(Alignment.TopEnd)
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                event.invoke(
                    PerfilContract.Event.getPerfil
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
            LazyColumn(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 270.dp, bottom = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item{
                    MyCorreo(usuario = state.usuario)
                }
                item{
                    MyPassword(usuario = state.usuario)
                }
                item{
                    Text("Mis productos", fontWeight = FontWeight.Bold,modifier=Modifier.fillMaxWidth(0.9f))
                    if(state.productos.isEmpty()){
                        NoProducts(state.refreshing)
                    }
                    Spacer(Modifier.padding(10.dp))
                }
                itemsIndexed(productos){index,producto->
                    MyProduct(
                        selectedProductoUrl = selectedProductoUrl ,
                        selectedProducto = selectedProducto,
                        navController = navController,
                        producto = producto,
                        index = index
                    )
                }

            }
        }
    }
}