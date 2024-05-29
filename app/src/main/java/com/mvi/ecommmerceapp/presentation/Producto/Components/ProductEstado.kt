package com.mvi.ecommmerceapp.presentation.Producto.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.ui.theme.cardBrown

@Composable
fun ProductEstado(
    estado:Int
){
    Column(modifier= Modifier.fillMaxWidth(0.9f)) {
        Row(){
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "estado",modifier= Modifier
                .size(20.dp)
                .padding(end = 5.dp))
            Text("Estado", fontWeight = FontWeight.Bold)
        }
        Text(estado.toString()+"/10",
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