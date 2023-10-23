package com.example.ecommmerceapp.presentation.QrScanner.Intent

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ecommmerceapp.UDF.UnidirectionalViewModel
import java.time.LocalTime

interface QrScannerContract:
    UnidirectionalViewModel<QrScannerContract.State, QrScannerContract.Event, QrScannerContract.Effect>{
        data class State @RequiresApi(Build.VERSION_CODES.O) constructor(
            val startEx: LocalTime ,
            val startRam : Long = 0L,
            val endRam: Long = 0L
        )

        sealed class Event{

            data class setStartEx(val time: LocalTime): Event()
            object getTimeExecution : Event()
            object getRamExecution : Event()
        }

        sealed class Effect{

        }
}