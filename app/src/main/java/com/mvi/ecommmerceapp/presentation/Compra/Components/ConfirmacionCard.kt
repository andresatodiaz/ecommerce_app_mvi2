package com.mvi.ecommmerceapp.presentation.Compra.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.ui.theme.cardBrown
import com.mvi.ecommmerceapp.ui.theme.mainBrown

@Composable
fun ConfirmacionCard(
    finalText:String,
    showSignature:MutableState<Boolean>
) {
    Text("Confirmación de compra",modifier=Modifier, fontWeight = FontWeight.Black)
    Text("Escriba su nombre ")
    Spacer(modifier = Modifier.padding(10.dp))
    Button(
    onClick = {
        showSignature.value=true
    },
    modifier= Modifier.fillMaxWidth(),
    colors= ButtonDefaults.buttonColors(
    containerColor = mainBrown,
    contentColor = Color.White
    )
    ) {
        Text("Escribir")
    }
    if(finalText!=""){
        Spacer(modifier = Modifier.padding(5.dp))
        Box(
            modifier= Modifier.background(cardBrown, RoundedCornerShape(20.dp))
        ){
            Text("Nombre " + finalText,modifier= Modifier
                .padding(10.dp)
                .align(
                    Alignment.Center
                ))
        }
    }
}