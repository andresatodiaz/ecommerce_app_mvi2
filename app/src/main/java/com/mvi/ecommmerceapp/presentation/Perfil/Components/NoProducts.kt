package com.mvi.ecommmerceapp.presentation.Perfil.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NoProducts (
    refreshing:Boolean
){
    if(refreshing){
        Text("No hay productos",modifier= Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(0.9f),color= Color.Gray)
    }else{
        Text("Cargando productos...",modifier= Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(0.9f),color= Color.Gray)
    }
}