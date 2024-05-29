package com.mvi.ecommmerceapp.presentation.Compra.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.presentation.Compra.Intent.CompraContract
import com.mvi.ecommmerceapp.ui.theme.complementaryBrown

@Composable
fun CompraFloatingButton(
    compraEvent: ()->Unit
) {
    ExtendedFloatingActionButton(
        shape= CircleShape,
        modifier= Modifier.padding(bottom = 70.dp),
        onClick = {
            compraEvent()
        },
        containerColor = complementaryBrown
    ) {
        Text(text = "Comprar", fontWeight = FontWeight.Black, modifier = Modifier.padding(end=10.dp))
        Icon(imageVector = Icons.Default.Check, contentDescription = "vender")
    }
}