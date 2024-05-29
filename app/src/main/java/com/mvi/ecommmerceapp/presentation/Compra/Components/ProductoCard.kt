package com.mvi.ecommmerceapp.presentation.Compra.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.domain.Entities.Producto

@Composable
fun ProductoCard(
    photo:String,
    producto: Producto
) {
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