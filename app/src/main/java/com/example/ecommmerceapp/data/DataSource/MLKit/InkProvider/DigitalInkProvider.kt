package com.example.ecommmerceapp.data.DataSource.MLKit.InkProvider

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.ecommmerceapp.data.Model.MLKitModelStatus
import com.example.ecommmerceapp.utils.MemoryConsumption
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class DigitalInkProviderImpl @Inject constructor(): DigitalInkProvider {

    override val predictions = Channel<List<String>>(4)

    private var strokeBuilder: Ink.Stroke.Builder = Ink.Stroke.builder()

    private val recognitionModel = DigitalInkRecognitionModel
        .builder(DigitalInkRecognitionModelIdentifier.JA)
        .build()

    private val remoteModelManager = RemoteModelManager.getInstance()

    private val recognizer = DigitalInkRecognition.getClient(
        DigitalInkRecognizerOptions
            .builder(this.recognitionModel)
            .build()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private var startTime = LocalTime.now()


    @RequiresApi(Build.VERSION_CODES.O)
    private var endTime = LocalTime.now()

    override fun checkIfModelIsDownlaoded(): Flow<MLKitModelStatus> = callbackFlow {
        trySend(MLKitModelStatus.CheckingDownload)

        this@DigitalInkProviderImpl.remoteModelManager
            .isModelDownloaded(this@DigitalInkProviderImpl.recognitionModel)
            .addOnSuccessListener { isDownloaded ->
                if (isDownloaded)
                    trySend(MLKitModelStatus.Downloaded)
                else
                    trySend(MLKitModelStatus.NotDownloaded)
            }
            .addOnCompleteListener { close() }
            .addOnFailureListener {
                it.printStackTrace()
                close(it)
            }

        awaitClose { cancel() }
    }

    override fun downloadModel(): Flow<MLKitModelStatus> = callbackFlow {
        val downloadConditions = DownloadConditions.Builder()
            .build()

        trySend(MLKitModelStatus.Downloading)
        this@DigitalInkProviderImpl.remoteModelManager
            .download(this@DigitalInkProviderImpl.recognitionModel, downloadConditions)
            .addOnSuccessListener {
                trySend(MLKitModelStatus.Downloaded)
            }
            .addOnCompleteListener { close() }
            .addOnFailureListener {
                it.printStackTrace()
                close(it)
            }

        awaitClose { cancel() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun record(x: Float, y: Float) {
        startTime= LocalTime.now()
        val point = Ink.Point.create(x, y)
        this.strokeBuilder.addPoint(point)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun finishRecording() {
        val stroke = this.strokeBuilder.build()

        val inkBuilder = Ink.builder()
        inkBuilder.addStroke(stroke)
        try{
            this.recognizer.recognize(inkBuilder.build())
                .addOnCompleteListener {
                    this.strokeBuilder = Ink.Stroke.builder()
                }
                .addOnSuccessListener {
                        result -> this.predictions.trySend(result.candidates.map { it.text })
                    Log.e("success",result.candidates.toString())
                    endTime= LocalTime.now()
                    Log.i("comp-ExecutionTime-Signature",Duration.between(startTime,endTime).toMillis().toString())
                    Log.i("comp-MemoryConsump-Signature",MemoryConsumption().getUsedMemorySize().toString())
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        }catch (e:Exception){
            Log.e("Error de stroke",e.message.toString())
        }

    }

    override fun close() {
        //this.recognizer.close()
    }
}

interface DigitalInkProvider {

    val predictions: Channel<List<String>>

    fun finishRecording()
    fun record(x: Float, y: Float)

    fun downloadModel(): Flow<MLKitModelStatus>
    fun checkIfModelIsDownlaoded(): Flow<MLKitModelStatus>

    fun close()
}