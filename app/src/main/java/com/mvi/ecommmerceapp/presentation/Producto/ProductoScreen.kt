package com.mvi.ecommmerceapp.presentation.Producto

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.MainApplication
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.presentation.Producto.Components.ProductBanner
import com.mvi.ecommmerceapp.presentation.Producto.Components.ProductDescription
import com.mvi.ecommmerceapp.presentation.Producto.Components.ProductDetails
import com.mvi.ecommmerceapp.presentation.Producto.Components.ProductEstado
import com.mvi.ecommmerceapp.presentation.Producto.Components.ProductPrice
import com.mvi.ecommmerceapp.presentation.Producto.Components.ProductVendedor
import com.mvi.ecommmerceapp.presentation.Producto.Intent.ProductoContract
import com.mvi.ecommmerceapp.presentation.Producto.ViewModel.ProductoViewModel

@Composable
fun ProductoScreen(
    photo: String,
    producto: Producto,
    navController: NavController,
    productoViewModel: ProductoViewModel,
) {
    val id = remember{ mutableStateOf("") }
    val (state,event)= use(productoViewModel)
    val usuario=state.usuario
    LaunchedEffect(key1 = true){
        event.invoke(
            ProductoContract.Event.OnGetData(producto.vendidoPor!!)
        )
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
        ProductBanner(
            photo=photo,
            titulo = producto.titulo
        )
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom=100.dp)
        ){
            item{
                ProductDescription(
                    descripcion = producto.descripcion
                )
            }
            item{
                ProductPrice(precio = producto.precio)
            }
            item{
                ProductEstado(
                    estado = producto.estado
                )
            }
            item{
                ProductVendedor(
                    id= producto.vendidoPor ?: "",
                    nombre = state.usuario.nombre,
                    apellido = state.usuario.apellido,
                    correo = state.usuario.correo
                )
            }
            item{
                ProductDetails(
                    vendidoPor = producto.vendidoPor ?: "",
                    id=usuario.id,
                    navController = navController,
                    compradoPor = producto.compradoPor ?: "",
                    producto = producto,
                    productEvent = event
                )

            }

        }
    }
}