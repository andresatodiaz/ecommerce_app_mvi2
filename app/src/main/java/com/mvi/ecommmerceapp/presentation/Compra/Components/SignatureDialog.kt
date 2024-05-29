package com.mvi.ecommmerceapp.presentation.Compra.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mvi.ecommmerceapp.presentation.Signature.DigitalInkViewModel
import com.mvi.ecommmerceapp.presentation.Signature.DrawSpace
import com.mvi.ecommmerceapp.ui.theme.mainBrown

@Composable
fun SignatureDialog(
    showSignature:MutableState<Boolean>,
    digitalInkState: DigitalInkViewModel.State,
    pointerEvent: (DigitalInkViewModel.Event)->Unit
) {
    Dialog(onDismissRequest = {showSignature.value=false }) {
        Box(
            modifier= Modifier
                .fillMaxWidth(0.95f)
                .background(Color.White, RoundedCornerShape(20.dp))
        ){
            Column(
                modifier= Modifier.padding(10.dp)
            ) {
                DrawSpace(
                    reset= digitalInkState.resetCanvas,
                    onDrawEvent = { pointerEvent.invoke(DigitalInkViewModel.Event.Pointer(it))},
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )
                Spacer(Modifier.padding(10.dp))
                Text("Recomendamos escribir su nombre letra por letra",modifier= Modifier.padding(5.dp))
                Text(digitalInkState.finalText,modifier= Modifier.fillMaxWidth(0.9f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {
                            pointerEvent.invoke(DigitalInkViewModel.Event.ResetText)
                        },
                        colors= ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Reiniciar")
                    }
                    Button(
                        onClick = {
                            showSignature.value=false
                        },
                        colors= ButtonDefaults.buttonColors(
                            containerColor = mainBrown,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Guardar")
                    }
                }


            }

        }

    }
}