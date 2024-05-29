package com.mvi.ecommmerceapp.presentation.Compra.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CompraBanner() {
    Column(
    modifier=Modifier.fillMaxWidth(0.9f),
    ){
        Row(
            modifier= Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "compra",modifier= Modifier.padding(end=5.dp))
            Text("Compra", fontWeight = FontWeight.Black, fontSize = 20.sp)
        }

    }
}