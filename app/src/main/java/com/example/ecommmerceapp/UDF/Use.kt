package com.example.ecommmerceapp.UDF

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.SharedFlow

@Composable
inline fun <reified STATE, EVENT, EFFECT> use(
    viewModel: UnidirectionalViewModel<STATE, EVENT, EFFECT>,
): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val state by viewModel.state.collectAsState()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }

    return StateDispatchEffect(
        state = state,
        effectFlow = viewModel.effect,
        dispatch = dispatch,
    )
}

data class StateDispatchEffect<STATE, EVENT, EFFECT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
    val effectFlow: SharedFlow<EFFECT>,
)