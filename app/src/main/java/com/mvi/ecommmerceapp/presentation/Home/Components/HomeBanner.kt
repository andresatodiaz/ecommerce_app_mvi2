package com.mvi.ecommmerceapp.presentation.Home.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun HomeBanner(
    colorMatrix:FloatArray
) {

    Card(modifier= Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(170.dp)) {
        Box(modifier= Modifier.fillMaxSize()){
            AsyncImage(model = "https://images.pexels.com/photos/18372347/pexels-photo-18372347/free-photo-of-modelo-textura-abstracto-marron.jpeg?auto=compress&cs=tinysrgb&w=300", contentDescription = "background",
                modifier= Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
            )
            Column(
                modifier= Modifier.padding(20.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)){
                            append("Antiq")
                        }
                        append("store")
                    },
                    color= Color.White,
                    fontSize = 30.sp
                )
                Text("Antiguedades al instante",color= Color.White,modifier= Modifier.padding(top=10.dp))
            }
        }
    }
}