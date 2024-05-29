package com.mvi.ecommmerceapp.presentation.Home.Components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun HomeSwipeIndicator(
   state: SwipeRefreshState,
   trigger: Dp
) {
    SwipeRefreshIndicator(
        state = state,
        refreshTriggerDistance = trigger,
        fade = true,
        contentColor = Color.LightGray,
        scale = true,
        backgroundColor = Color.White,
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 100))
    )
}