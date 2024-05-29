package com.mvi.ecommmerceapp.presentation.Home.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.domain.Entities.Usuario

@Composable
fun UserBanner(
    usuario:Usuario
){
    Row(
        modifier= Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(model = "https://picsum.photos/id/${200}/200/200/?blur=5", contentDescription = "user",
            modifier = Modifier
                .clip(CircleShape)
                .size(15.dp)
        )
        Spacer(Modifier.padding(end=5.dp))
        Text(text= buildAnnotatedString {
            append("Bienvenido ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                append(usuario.nombre.capitalize()+" "+usuario.apellido.capitalize())
            }

        },color= Color.Black)
    }
}