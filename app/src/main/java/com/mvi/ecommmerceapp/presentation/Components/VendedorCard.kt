package com.mvi.ecommmerceapp.presentation.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.ui.theme.cardBrown

@Composable
fun VendedorCard(
    nombre:String,
    apellido:String,
    correo:String
) {
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
            Column(modifier= Modifier.padding(start=10.dp)) {
                Text(nombre.capitalize()+" "+apellido.capitalize())
                Text(correo, fontWeight = FontWeight.Black)
            }

        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
}