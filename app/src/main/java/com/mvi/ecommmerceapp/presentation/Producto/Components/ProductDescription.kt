package com.mvi.ecommmerceapp.presentation.Producto.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.domain.Entities.Producto

@Composable
fun ProductDescription(
    descripcion:String
) {
    Column(modifier= Modifier.fillMaxWidth(0.9f)) {
        Row{
            Icon(imageVector = Icons.Default.Info, contentDescription = "descripcion",modifier= Modifier
                .size(20.dp)
                .padding(end = 5.dp))
            Text("Descripción", fontWeight = FontWeight.Bold)
        }
        Text(descripcion)
    }
    Spacer(Modifier.padding(10.dp))
}