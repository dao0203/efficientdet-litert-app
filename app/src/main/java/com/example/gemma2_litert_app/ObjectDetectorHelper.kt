package com.example.gemma2_litert_app

import android.content.Context
import android.graphics.Bitmap
import com.example.gemma2_litert_app.domain.DetectionResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.InterpreterApi.Options.TfLiteRuntime
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.nio.FloatBuffer
import javax.inject.Inject

class ObjectDetectorHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun runInference(input: Bitmap): DetectionResult {
        return withContext(Dispatchers.IO) {
            val interpreterOption = InterpreterApi
                .Options()
                .setRuntime(TfLiteRuntime.FROM_SYSTEM_ONLY)
            val file = FileUtil.loadMappedFile(
                context,
                "model/EfficientDet-Lite0.tflite"
            )
            val interpreter = InterpreterApi.create(file, interpreterOption)

            // 出力用のバッファを用意
            val maxDetections = 25
            val location = FloatBuffer.allocate(maxDetections * 4)
            val category = FloatBuffer.allocate(maxDetections)
            val score = FloatBuffer.allocate(maxDetections)
            val numberOfDetection = FloatBuffer.allocate(1)

            val outputBuffer = mapOf(
                0 to location,
                1 to category,
                2 to score,
                3 to numberOfDetection,
            )
            // 入力用の画像を変換するためのTensorImageを用意
            val tensorImage = TensorImage(DataType.UINT8)
            // 入力画像をTensorImageに読み込む
            tensorImage.load(input)
            // リサイズと正規化の処理を行う
            val imageProcessor = ImageProcessor
                .Builder()
                // リサイズ, 224x224に変換, バイリニア補間
                .add(ResizeOp(320, 320, ResizeOp.ResizeMethod.BILINEAR))
                .build()
            // 前処理を行った画像を取得
            val processedImage = imageProcessor.process(tensorImage)
            interpreter.runForMultipleInputsOutputs(arrayOf(processedImage.buffer), outputBuffer)
            println("location: ${location.array()[0]}")
            println("category: ${category.array()[0]}")
            return@withContext DetectionResult(
                location = location.array(),
                category = category.array(),
                score = score.array(),
                numberOfDetection = numberOfDetection.array()
            )
        }
    }
}
