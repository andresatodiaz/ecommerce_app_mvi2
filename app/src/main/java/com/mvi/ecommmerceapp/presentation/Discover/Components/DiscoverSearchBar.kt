package com.mvi.ecommmerceapp.presentation.Discover.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.ui.theme.cardBrown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverSearchBar(
    searchName:MutableState<String>
) {
    OutlinedTextField(
    value = searchName.value,
    onValueChange = {searchName.value=it},
    modifier= Modifier
    .fillMaxWidth()
    .padding(top = 10.dp, bottom = 20.dp),
    shape= CircleShape,
    placeholder = { Text("Buscar..") },
    singleLine = true,
    leadingIcon = {
        IconButton(
            modifier = Modifier.alpha(0.5f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "búsqueda icono",
                tint = Color.Gray
            )
        }
    },
    trailingIcon = {
        if(searchName.value != ""){
            IconButton(onClick = {
                searchName.value = ""}
            ){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close búsqueda",
                    tint = Color.LightGray
                )
            }
        }
    },
    keyboardOptions = KeyboardOptions(
    imeAction = ImeAction.Search
    ),
    colors = TextFieldDefaults.textFieldColors(
    containerColor = cardBrown,
    unfocusedIndicatorColor = Color.Transparent
    )
    )
}