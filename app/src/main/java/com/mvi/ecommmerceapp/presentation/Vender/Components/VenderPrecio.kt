package com.mvi.ecommmerceapp.presentation.Vender.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenderPrecio(
    precio:MutableState<String>
) {
    Column(modifier=Modifier.fillMaxWidth(0.9f)) {
        Row(){
            Icon(painter = painterResource(id = R.drawable.price), contentDescription = "price",modifier= Modifier
                .size(20.dp)
                .padding(end = 5.dp))
            Text("Precio", fontWeight = FontWeight.Bold)
        }
        OutlinedTextField(
            value = precio.value,
            onValueChange = {precio.value=it},
            modifier= Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape= CircleShape
        )
    }
    Spacer(Modifier.padding(10.dp))

}