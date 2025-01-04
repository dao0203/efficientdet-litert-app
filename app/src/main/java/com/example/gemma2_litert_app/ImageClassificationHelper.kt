package com.example.gemma2_litert_app

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.android.gms.tflite.java.TfLite
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.InterpreterApi.Options.TfLiteRuntime
import java.io.File
import javax.inject.Inject

class ImageClassificationHelper @Inject constructor(
    private val context: Context
) {
    private lateinit var interpreter: InterpreterApi
    val initializationTask: Task<Void> by lazy {
        TfLite.initialize(context)
            .addOnSuccessListener {
                val interpreterOption = InterpreterApi
                    .Options()
                    .setRuntime(TfLiteRuntime.FROM_SYSTEM_ONLY)
                interpreter = InterpreterApi
                    .create(
                        File("assets/model/EfficientNet.tflite"),
                        interpreterOption
                    )
            }
    }

    fun runInference(input: Any): Any {
       interpreter.runForMultipleInputsOutputs()
    }
}