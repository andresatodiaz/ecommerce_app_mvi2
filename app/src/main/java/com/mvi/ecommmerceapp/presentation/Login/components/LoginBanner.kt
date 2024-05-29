package com.mvi.ecommmerceapp.presentation.Login.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.mvi.ecommmerceapp.ui.theme.colorMatrix
@Composable
fun LoginBanner(){
    Box(
    modifier=Modifier.fillMaxWidth()
    .height(100.dp)
    ){
        AsyncImage(model = "https://picsum.photos/id/${250}/200/200/?blur=5", contentDescription = "background",
            modifier= Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Text(buildAnnotatedString {
            withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)){
                append("Antiq")
            }
            append("store")
        },color= Color.White, modifier = Modifier.align(Alignment.Center), fontSize = 20.sp)
    }
}