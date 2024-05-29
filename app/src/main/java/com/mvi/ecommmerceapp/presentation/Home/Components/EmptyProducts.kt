package com.mvi.ecommmerceapp.presentation.Home.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyProducts (
    refreshing:Boolean
){
    if (refreshing){
        Text("Cargando productos...", modifier = Modifier.padding(top=10.dp,start=10.dp))
    }else{
        Text("No hay productos...", modifier = Modifier.padding(top=10.dp,start=10.dp))
    }
}