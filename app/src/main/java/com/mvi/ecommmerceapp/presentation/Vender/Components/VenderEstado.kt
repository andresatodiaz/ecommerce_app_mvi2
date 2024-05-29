package com.mvi.ecommmerceapp.presentation.Vender.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.ui.theme.cardBrown

@Composable
fun VenderEstado(
    estado:MutableState<String>,
    expanded:MutableState<Boolean>,
    tablero:List<Int>
){
    Column(modifier= Modifier.fillMaxWidth(0.9f)) {
        Row(){
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "estado",modifier= Modifier
                .size(20.dp)
                .padding(end = 5.dp))
            Text("Estado", fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier
                .clickable(onClick = {
                    expanded.value = true
                })
                .background(
                    cardBrown,
                    CircleShape
                )
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text=estado.value+" /10",
                fontWeight = FontWeight.Bold,
                textAlign= TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                modifier= Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(
                    cardBrown
                ),
            offset = DpOffset(0.dp,5.dp)
        ) {
            tablero.forEachIndexed { _, item ->
                DropdownMenuItem(
                    text={
                        Text(item.toString())
                    },
                    onClick = {
                        estado.value = item.toString()
                        expanded.value = false }
                )
            }
        }
    }
    Spacer(Modifier.padding(10.dp))
}