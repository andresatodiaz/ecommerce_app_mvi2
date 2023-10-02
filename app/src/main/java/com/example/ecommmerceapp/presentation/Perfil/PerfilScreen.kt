package com.example.ecommmerceapp.presentation.Perfil

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecommmerceapp.LoginActivity
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.presentation.Perfil.ViewModel.PerfilViewModel
import com.example.ecommmerceapp.ui.theme.complementaryBrown
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    perfilViewModel: PerfilViewModel
) {
    val context= LocalContext.current
    val coroutine = rememberCoroutineScope()
    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    LaunchedEffect(key1 = true){
        if(perfilViewModel.myUser.value.nombre.isEmpty()){
            perfilViewModel.getMyUser()
        }
        Log.i("nombre",perfilViewModel.myUser.value.nombre.toString())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(model = "https://picsum.photos/id/${200}/200/200/?blur=5", contentDescription = "background",
            modifier= Modifier
                .fillMaxWidth()
                .height(230.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Text(perfilViewModel.myUser.value.nombre.capitalize()+" "+perfilViewModel.myUser.value.apellido.capitalize(),
            modifier= Modifier.padding(top=150.dp, start=15.dp).fillMaxWidth(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 30.sp
        )
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 270.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Correo", fontWeight = FontWeight.Bold)
                    Text(perfilViewModel.myUser.value.correo)
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Contrasena", fontWeight = FontWeight.Bold)
                    Text(perfilViewModel.myUser.value.contrasena)
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Button(
                    onClick = {
                        coroutine.launch {
                            val editor = MainApplication.applicationContext().getSharedPreferences(
                                "preferences",
                                Context.MODE_PRIVATE
                            ).edit()
                            editor.putString("LOGGED_ID","")
                            editor.apply()
                            context.startActivity(Intent(context, LoginActivity::class.java))
                            finishActivity
                            flagKillActivity.value=true
                        }
                    },
                    modifier= Modifier.fillMaxWidth(0.9f),
                    colors= ButtonDefaults.buttonColors(
                        containerColor = complementaryBrown,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Log out")
                }
            }

        }
    }
}