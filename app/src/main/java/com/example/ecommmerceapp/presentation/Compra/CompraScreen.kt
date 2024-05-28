package com.example.ecommmerceapp.presentation.Compra

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.domain.Entities.Producto
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import com.example.ecommmerceapp.presentation.QrScanner.ViewModel.QrScannerViewModel
import com.example.ecommmerceapp.presentation.Signature.DigitalInkViewModel
import com.example.ecommmerceapp.presentation.Signature.DrawSpace
import com.example.ecommmerceapp.presentation.Signature.use
import com.example.ecommmerceapp.ui.theme.cardBrown
import com.example.ecommmerceapp.ui.theme.complementaryBrown
import com.example.ecommmerceapp.ui.theme.mainBrown
import com.example.ecommmerceapp.utils.MemoryConsumption
import com.example.ecommmerceapp.UDF.use
import com.example.ecommmerceapp.presentation.Compra.Intent.CompraContract
import com.example.ecommmerceapp.presentation.Compra.ViewModel.CompraViewModel
import com.example.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.example.ecommmerceapp.presentation.QrScanner.Intent.QrScannerContract
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraScreen(
    photo: String,
    producto: Producto,
    navController: NavController,
    compraViewModel: CompraViewModel,
    qrScannerViewModel: QrScannerViewModel,
    digitalInkViewModel: DigitalInkViewModel
) {
    val showSignature = remember{ mutableStateOf(false) }
    val resetCanvas = remember{ mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val (state, event) = use(digitalInkViewModel, DigitalInkViewModel.State())
    val (compraState,compraEvent,compraEffect)= use(compraViewModel)
    val (qrState,qrEvent,qrEffect)= use(qrScannerViewModel)

    LaunchedEffect(key1 = true ){
        event(DigitalInkViewModel.Event.ResetText)
        qrEvent.invoke(
            QrScannerContract.Event.getTimeExecution
        )
        qrEvent.invoke(
            QrScannerContract.Event.getRamExecution
        )
    }

    DisposableEffect(Unit) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP)
                event(DigitalInkViewModel.Event.OnStop)
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose { lifecycleOwner.lifecycle.removeObserver(lifecycleObserver) }
    }

    val discountLink = remember{ mutableStateOf("") }


    LaunchedEffect(key1 = true){
        compraEvent.invoke(
            CompraContract.Event.onGetVendedor(producto.vendidoPor!!)
        )
        discountLink.value=qrScannerViewModel.qrLink.value
        Log.i("barcode2",qrScannerViewModel.qrLink.value)

    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                shape= CircleShape,
                modifier=Modifier.padding(bottom = 70.dp),
                onClick = {
                    compraEvent.invoke(
                        CompraContract.Event.onComprarProducto(producto)
                    )

                    navController.navigate("home")
                          },
                containerColor = complementaryBrown
            ) {
                Text(text = "Comprar", fontWeight = FontWeight.Black, modifier = Modifier.padding(end=10.dp))
                Icon(imageVector = Icons.Default.Check, contentDescription = "vender")
            }
        }
    ) {
        Box(Modifier.fillMaxSize()){
            LazyColumn(
                modifier=Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top=20.dp, bottom = 100.dp)
            ){
                item{
                    Column(
                        modifier=Modifier.fillMaxWidth(0.9f),
                    ){
                        Row(
                            modifier=Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "compra",modifier=Modifier.padding(end=5.dp))
                            Text("Compra", fontWeight = FontWeight.Black, fontSize = 20.sp)
                        }

                    }
                }
                item{
                    Column(
                        modifier=Modifier.fillMaxWidth(0.9f)
                    ){
                        Text("Vendido por",modifier=Modifier.padding(10.dp), fontWeight = FontWeight.Black)
                    }
                }
                item{
                    Column(
                        modifier= Modifier
                            .fillMaxWidth(0.9f)
                            .background(cardBrown, RoundedCornerShape(20.dp))
                    ) {
                        Row(
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            AsyncImage(model = "https://picsum.photos/id/${200}/200/200/?blur=5", contentDescription = "user",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(100.dp)
                            )
                            Column(modifier=Modifier.padding(start=10.dp)) {
                                Text(compraState.vendedor.nombre.capitalize()+" "+compraState.vendedor.apellido.capitalize())
                                Text(compraState.vendedor.correo, fontWeight = FontWeight.Black)
                            }

                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                item{
                    Column(
                        modifier=Modifier.fillMaxWidth(0.9f)
                    ){
                        Text("Datos del producto",modifier=Modifier.padding(10.dp), fontWeight = FontWeight.Black)
                    }
                }
                item{
                    Column(modifier=Modifier.fillMaxWidth(0.9f)) {
                        AsyncImage(model = photo, contentDescription = "background",
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(){
                            Text("Título", fontWeight = FontWeight.Black, modifier = Modifier.width(100.dp))
                            Text(producto.titulo)
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(){
                            Text("Descripción", fontWeight = FontWeight.Black, modifier = Modifier.width(100.dp))
                            Text(producto.descripcion)
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(){
                            Text("Precio",fontWeight = FontWeight.Black, modifier = Modifier.width(100.dp))
                            Text(producto.precio+" $")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
                item{
                    Column(
                        modifier= Modifier
                            .fillMaxWidth(0.9f)
                            .padding(10.dp)
                    ){
                        Text("Obtener descuento",modifier=Modifier, fontWeight = FontWeight.Black)
                        Text("Escanear QR único")


                        if(discountLink.value!=""){
                            Spacer(modifier = Modifier.padding(10.dp))
                            Box(
                                modifier=Modifier.background(cardBrown, RoundedCornerShape(20.dp))
                            ){
                                Text("Link de descuento " + discountLink.value,modifier= Modifier
                                    .padding(10.dp)
                                    .align(
                                        Alignment.Center
                                    ))
                            }

                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            onClick = {
                                navController.navigate("qrscanner")
                                qrEvent.invoke(
                                    QrScannerContract.Event.setStartEx(LocalTime.now())
                                )
                            },
                            modifier= Modifier.fillMaxWidth(),
                            colors= ButtonDefaults.buttonColors(
                                containerColor = mainBrown,
                                contentColor = Color.White
                            )
                        ) {
                            if(discountLink.value!=""){
                                Text("Volver a escanear")
                            }else{
                                Text("Escanear")
                            }

                        }
                        Spacer(modifier=Modifier.padding(10.dp))
                        Text("Confirmación de compra",modifier=Modifier, fontWeight = FontWeight.Black)
                        Text("Escriba su nombre ")
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            onClick = {
                                      showSignature.value=true
                            },
                            modifier= Modifier.fillMaxWidth(),
                            colors= ButtonDefaults.buttonColors(
                                containerColor = mainBrown,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Escribir")
                        }
                        if(state.finalText!=""){
                            Spacer(modifier = Modifier.padding(5.dp))
                            Box(
                                modifier=Modifier.background(cardBrown, RoundedCornerShape(20.dp))
                            ){
                                Text("Nombre " + state.finalText,modifier= Modifier
                                    .padding(10.dp)
                                    .align(
                                        Alignment.Center
                                    ))
                            }

                        }
                    }


                }
            }
        }
    }
    if(showSignature.value){
        Dialog(onDismissRequest = {showSignature.value=false }) {
            Box(
                modifier= Modifier
                    .fillMaxWidth(0.95f)
                    .background(Color.White, RoundedCornerShape(20.dp))
            ){
                Column(
                    modifier=Modifier.padding(10.dp)
                ) {
                    DrawSpace(
                        reset= state.resetCanvas,
                        onDrawEvent = { event(DigitalInkViewModel.Event.Pointer(it)) },
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                    Spacer(Modifier.padding(10.dp))
                    Text("Recomendamos escribir su nombre letra por letra",modifier=Modifier.padding(5.dp))
                    Text(state.finalText,modifier=Modifier.fillMaxWidth(0.9f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(
                            onClick = {
                                event(DigitalInkViewModel.Event.ResetText)
                            },
                            colors= ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Reiniciar")
                        }
                        Button(
                            onClick = {
                                showSignature.value=false
                            },
                            colors= ButtonDefaults.buttonColors(
                                containerColor = mainBrown,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Guardar")
                        }
                    }


                }

            }

        }
    }


}