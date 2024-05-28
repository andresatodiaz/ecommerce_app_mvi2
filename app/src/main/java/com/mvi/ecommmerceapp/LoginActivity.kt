package com.mvi.ecommmerceapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mvi.ecommmerceapp.ui.theme.ECommmerceAppTheme
import com.mvi.ecommmerceapp.ui.theme.mainBrown
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mvi.ecommmerceapp.presentation.Login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECommmerceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sp = MainApplication.applicationContext().getSharedPreferences(
                        "preferences",
                        Context.MODE_PRIVATE
                    )

                    if(sp.getString("LOGGED_ID","")!=""){
                        goToMain()
                    }

                    val uiController = rememberSystemUiController()
                    uiController.setSystemBarsColor(mainBrown)
                    uiController.setNavigationBarColor(mainBrown)

                    LoginScreen( ){
                        goToMain()
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalGetImage::class) private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}