package com.mvi.ecommmerceapp.presentation.Perfil.Components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mvi.ecommmerceapp.LoginActivity
import com.mvi.ecommmerceapp.MainApplication
import com.mvi.ecommmerceapp.ui.theme.complementaryBrown
import kotlinx.coroutines.launch

@Composable
fun LogOutButton(
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    modifier: Modifier
){
    val coroutine= rememberCoroutineScope()
    val context= LocalContext.current

    Button(
    shape= CircleShape,
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
    modifier= modifier
    .padding(end = 20.dp, top = 20.dp),
    colors= ButtonDefaults.buttonColors(
    containerColor = complementaryBrown,
    contentColor = Color.Black
    )
    ) {
        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "log out",modifier= Modifier.size(20.dp))
    }
}