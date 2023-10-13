package com.example.ecommmerceapp.presentation.Signature

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.data.MLKitModelStatus
import com.example.ecommmerceapp.data.Provider.DigitalInkProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigitalInkViewModelImpl @Inject constructor(
    private val digitalInkProvider: DigitalInkProvider,
): ViewModel(), DigitalInkViewModel {

    private var finishRecordingJob: Job? = null

    private val _digitalInkModelStatus = digitalInkProvider.checkIfModelIsDownlaoded()
        .flatMapLatest { status ->
            if (status == MLKitModelStatus.Downloaded)
                flowOf(status)
            else
                digitalInkProvider.downloadModel()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, MLKitModelStatus.NotDownloaded)



    private val _predictions = digitalInkProvider.predictions
        .consumeAsFlow()
        .onEach {
            if (it.isEmpty())
                return@onEach

            setFinalText(text = _finalText.value.plus(it[0]))
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    private val _finalText = MutableStateFlow<String>("")
    private val _resetCanvas = MutableStateFlow<Boolean>(false)

    override val state: StateFlow<DigitalInkViewModel.State>
        get() = combine(
            _digitalInkModelStatus,
            _resetCanvas,
            _predictions,
            _finalText
        ) { result ->
            val digitalInkModelStatus = result[0] as MLKitModelStatus
            val resetCanvas = result[1] as Boolean
            val predictions = result[2] as List<String>
            val finalText = result[3] as String
            val areModelsDownloaded = digitalInkModelStatus == MLKitModelStatus.Downloaded

            Log.e("success1",finalText)

            DigitalInkViewModel.State(
                resetCanvas = resetCanvas,
                showModelStatusProgress = !areModelsDownloaded,
                finalText = finalText,
                predictions = predictions
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DigitalInkViewModel.State())

    override fun onEvent(event: DigitalInkViewModel.Event) {

        when (event) {
            is DigitalInkViewModel.Event.Pointer -> {

                when (val drawEvent = event.event) {
                    is DrawEvent.Down -> {
                        this.finishRecordingJob?.cancel()
                        _resetCanvas.value = false

                        digitalInkProvider.record(drawEvent.x, drawEvent.y)
                    }

                    is DrawEvent.Move -> {
                        digitalInkProvider.record(drawEvent.x, drawEvent.y)
                    }

                    is DrawEvent.Up -> {
                        this.finishRecordingJob = viewModelScope.launch {
                            delay(DEBOUNCE_INTERVAL)
                            _resetCanvas.value = true
                            digitalInkProvider.finishRecording()
                        }
                    }

                    else -> {}
                }
            }

            is DigitalInkViewModel.Event.OnStop -> {
                digitalInkProvider.close()
            }

            is DigitalInkViewModel.Event.TextChanged -> {
                setFinalText(event.text)
            }

            is DigitalInkViewModel.Event.PredictionSelected -> {
                setFinalText(text = _finalText.value.dropLast(1).plus(event.prediction))
            }

            is DigitalInkViewModel.Event.ResetText->{
                setFinalText(text="")
            }

            else -> {}
        }
    }

    private fun setFinalText(text: String) {
        _finalText.value = text


    }

    companion object {
        private const val DEBOUNCE_INTERVAL = 1000L
    }
}

interface DigitalInkViewModel: SingleFlowViewModel<DigitalInkViewModel.Event, DigitalInkViewModel.State> {
    data class State(
        val resetCanvas: Boolean = false,
        val showModelStatusProgress: Boolean = false,
        val finalText: String = "",
        val predictions: List<String> = emptyList(),
    )

    sealed class Event {
        data class TextChanged(val text: String): Event()
        data class Pointer(val event: DrawEvent): Event()
        data class PredictionSelected(val prediction: String): Event()
        object ResetText:Event()

        object OnStop: Event()

    }
}

val LocalDigitalInkViewModel = compositionLocalOf<DigitalInkViewModel> {
    error("LocalDigitalViewModelFactory not provided")
}

@Composable
fun provideDigitalInkViewModel(viewModelFactory: @Composable () -> DigitalInkViewModel)
        = LocalDigitalInkViewModel provides viewModelFactory.invoke()