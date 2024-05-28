package com.mvi.ecommmerceapp.presentation.Producto

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.MainApplication
import com.mvi.ecommmerceapp.R
import com.mvi.ecommmerceapp.UDF.use
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.presentation.Producto.Intent.ProductoContract
import com.mvi.ecommmerceapp.presentation.Producto.ViewModel.ProductoViewModel
import com.mvi.ecommmerceapp.ui.theme.cardBrown
import com.mvi.ecommmerceapp.ui.theme.complementaryBrown
import com.mvi.ecommmerceapp.ui.theme.priceColor
import kotlinx.coroutines.launch

@Composable
fun ProductoScreen(
    photo: String,
    producto: Producto,
    navController: NavController,
    productoViewModel: ProductoViewModel,
    showQRScanner:MutableState<Boolean>
) {
    val (state,event,effect)= use(productoViewModel)

    LaunchedEffect(key1 = true){
        Log.i("producto",producto.vendidoPor!!.toString())
        event.invoke(
            ProductoContract.Event.onGetData(producto.vendidoPor!!)
        )
    }

    Log.i("photo",photo)
    val coroutine = rememberCoroutineScope()
    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    val id = remember{ mutableStateOf("") }
    LaunchedEffect(key1 = true){
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
        AsyncImage(model = photo, contentDescription = "background",
            modifier= Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Text(producto.titulo,modifier= Modifier.padding(top=80.dp), fontWeight = FontWeight.Black, color = Color.White, fontSize = 20.sp)
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom=100.dp)
        ){
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Row(){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "descripcion",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Descripción", fontWeight = FontWeight.Bold)
                    }
                    Text(producto.descripcion)
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Row(){
                        Icon(painter = painterResource(id = R.drawable.price), contentDescription = "price",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Precio", fontWeight = FontWeight.Bold)
                    }
                    Text(producto.precio+" $",modifier= Modifier
                        .background(
                            priceColor,
                            CircleShape
                        )
                        .padding(10.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Row(){
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "estado",modifier= Modifier
                            .size(20.dp)
                            .padding(end = 5.dp))
                        Text("Estado", fontWeight = FontWeight.Bold)
                    }
                    Text(producto.estado.toString()+"/10",
                        modifier= Modifier
                            .background(
                                cardBrown,
                                CircleShape
                            )
                            .padding(10.dp),
                        fontWeight = FontWeight.Bold
                        )

                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Vendido por", fontWeight = FontWeight.Bold, modifier=Modifier.padding(bottom=10.dp))
                    Column(
                        modifier= Modifier
                            .background(cardBrown, RoundedCornerShape(20.dp))
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        if( state.vendedor.id==""){
                            Text("Cargando vendedor")
                        }else{
                            Text(state.vendedor.nombre.capitalize()+" "+state.vendedor.apellido.capitalize())
                            Text(state.vendedor.correo, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(
                    modifier=Modifier.fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.End
                ){
                    Column(modifier=Modifier.width(150.dp)){
                        Button(
                            enabled = producto.vendidoPor!=state.usuario.id,
                            onClick = {
                                if(producto.compradoPor!=state.usuario.id){
                                    navController.navigate("compra")
                                }else{
                                    event.invoke(
                                        ProductoContract.Event.onDeshacerCompra(producto)
                                    )

                                    navController.navigate("home")
                                }

                            },
                            modifier= Modifier.fillMaxWidth(),
                            colors= ButtonDefaults.buttonColors(
                                containerColor = complementaryBrown,
                                contentColor = Color.Black
                            )
                        ) {
                            if(producto.compradoPor!=state.usuario.id){
                                Text("Comprar")
                            }else{
                                Text("Cancelar compra")
                            }
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "compra",modifier=Modifier.padding(5.dp))
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        if(producto.vendidoPor==state.usuario.id){
                            Button(
                                onClick = {
                                    coroutine.launch {
                                        event.invoke(
                                            ProductoContract.Event.onEliminarProducto(producto)
                                        )
                                        navController.navigate("home")
                                    }
                                },
                                modifier= Modifier.fillMaxWidth(),
                                colors= ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Borrar")
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete",modifier=Modifier.padding(5.dp))
                            }
                        }
                    }
                }

            }

        }
    }
}