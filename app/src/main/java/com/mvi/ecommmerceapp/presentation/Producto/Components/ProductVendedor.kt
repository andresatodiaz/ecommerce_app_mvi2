package com.mvi.ecommmerceapp.presentation.Producto.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.ui.theme.cardBrown

@Composable
fun ProductVendedor(
    id:String,
    nombre:String,
    apellido:String,
    correo:String
) {
    Column(modifier= Modifier.fillMaxWidth(0.9f)) {
        Text("Vendido por", fontWeight = FontWeight.Bold, modifier= Modifier.padding(bottom=10.dp))
        Column(
            modifier= Modifier
                .background(cardBrown, RoundedCornerShape(20.dp))
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            if(id==""){
                Text("Cargando vendedor")
            }else{
                Text(nombre.capitalize()+" "+apellido.capitalize())
                Text(correo, fontWeight = FontWeight.Bold)
            }
        }
    }
    Spacer(Modifier.padding(10.dp))
}