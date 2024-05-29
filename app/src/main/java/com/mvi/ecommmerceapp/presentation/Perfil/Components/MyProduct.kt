package com.mvi.ecommmerceapp.presentation.Perfil.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.ui.theme.cardBrown
import com.mvi.ecommmerceapp.ui.theme.colorMatrix

@Composable
fun MyProduct(
    selectedProductoUrl:MutableState<String>,
    selectedProducto:MutableState<Producto>,
    navController:NavController,
    producto:Producto,
    index:Int
) {
    Card(
        modifier= Modifier
            .padding(5.dp, 5.dp, 5.dp, 10.dp)
            .clickable {
                selectedProducto.value = producto
                selectedProductoUrl.value =
                    "https://picsum.photos/id/${index}/200/200/?blur=2"
                navController.navigate("producto")
            }
    ) {
        Box(
            modifier= Modifier
                .fillMaxSize(0.9f)
                .background(cardBrown)
        ){
            AsyncImage(model = "https://picsum.photos/id/${index}/200/200/?blur=2", contentDescription = "background",
                modifier= Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
            )
            Column(
                modifier= Modifier.padding(top=90.dp)
            ) {
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