package com.mvi.ecommmerceapp.presentation.Home.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.ui.theme.complementaryBrown

@Composable
fun HomeFloatingButton(
    navController:NavController
) {
    FloatingActionButton(
        shape= CircleShape,
        modifier= Modifier.padding(bottom = 70.dp),
        containerColor = complementaryBrown,
        onClick = {
            navController.navigate("vender")
        }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "vender")
    }
}