package com.mvi.ecommmerceapp.presentation.Perfil.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.domain.Entities.Usuario
import com.mvi.ecommmerceapp.ui.theme.colorMatrix

@Composable
fun ProfileBanner(
    usuario: Usuario
) {
    AsyncImage(model = "https://picsum.photos/id/${200}/200/200/?blur=5", contentDescription = "background",
        modifier= Modifier
            .fillMaxWidth()
            .height(230.dp),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
    )
    Row(
        modifier= Modifier
            .padding(top = 150.dp, start = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "person",tint= Color.White,modifier= Modifier.padding(end=10.dp))
        Text(usuario.nombre.capitalize()+" "+usuario.apellido.capitalize(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 30.sp
        )
    }
}