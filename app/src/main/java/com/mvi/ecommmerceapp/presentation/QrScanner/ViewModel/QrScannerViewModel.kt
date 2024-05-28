package com.mvi.ecommmerceapp.presentation.QrScanner.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.ecommmerceapp.presentation.QrScanner.Intent.QrScannerContract
import com.mvi.ecommmerceapp.utils.MemoryConsumption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(

):ViewModel(), QrScannerContract {
    val qrLink = mutableStateOf("")


    @RequiresApi(Build.VERSION_CODES.O)
    private val mutableState = MutableStateFlow(QrScannerContract.State(LocalTime.now()))
    @RequiresApi(Build.VERSION_CODES.O)
    override val state: StateFlow<QrScannerContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<QrScannerContract.Effect>()
    override val effect: SharedFlow<QrScannerContract.Effect> =
        effectFlow.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: QrScannerContract.Event) = when(event){
        is QrScannerContract.Event.setStartEx->
            setStarEx()
        is QrScannerContract.Event.getTimeExecution->
            getExecutionTime()
        is QrScannerContract.Event.getRamExecution->
            getMemoryUsage()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setStarEx(){
        viewModelScope.launch {
            mutableState.update {
                QrScannerContract.State(startEx = LocalTime.now())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getExecutionTime(){
        Log.i("comp-ExecutionTime-Qr", Duration.between(mutableState.value.startEx,LocalTime.now()).toMillis().toString())
    }

    fun getMemoryUsage(){
        Log.i("comp-MemoryConsumpQr", MemoryConsumption().getUsedMemorySize().toString())
    }
}
