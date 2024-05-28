package com.mvi.ecommmerceapp.presentation.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mvi.ecommmerceapp.presentation.Login.ViewModel.LoginViewModel
import com.mvi.ecommmerceapp.presentation.Login.components.Login
import com.mvi.ecommmerceapp.presentation.Login.components.Register

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    goToMain:()-> Unit
) {
    val correo = remember{ mutableStateOf("") }
    val contrasena = remember{ mutableStateOf("") }

    val nombres = remember{ mutableStateOf("")}
    val apellidos = remember{ mutableStateOf("")}


    val coroutine= rememberCoroutineScope()
    val loginMenu = remember{ mutableStateOf(0) }

    val focusManager = LocalFocusManager.current

    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )

    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(0xffeeeeee))
            .clickable { focusManager.clearFocus() }
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth(0.8f)
                .background(Color.White, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center)
        ) {
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
                },color=Color.White, modifier = Modifier.align(Alignment.Center), fontSize = 20.sp)
            }

            Spacer(Modifier.padding(10.dp))
            if(loginMenu.value==0){
                Login(
                    correo = correo,
                    contrasena = contrasena,
                    loginViewModel = loginViewModel,
                    loginMenu = loginMenu,
                    goToMain = goToMain
                )
            }else{
                Register(
                    correo = correo,
                    contrasena = contrasena,
                    nombres=nombres,
                    apellidos=apellidos,
                    loginViewModel = loginViewModel,
                    loginMenu = loginMenu
                )
            }

        }
    }
}