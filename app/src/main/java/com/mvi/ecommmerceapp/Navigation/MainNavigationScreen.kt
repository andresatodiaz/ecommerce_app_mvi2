package com.mvi.ecommmerceapp.Navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mvi.ecommmerceapp.Navigation.BottomNav.BottomNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationScreen (
    navController : NavHostController = rememberNavController(),
    finishActivity : Unit,
    flagKillActivity : MutableState<Boolean>,
    showQRScanner: MutableState<Boolean>
){
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Box(modifier=Modifier.fillMaxHeight()){
            MainNavigationGraph(
                navController,
                finishActivity ,
                flagKillActivity,
                showQRScanner=showQRScanner
            )
        }

    }

}