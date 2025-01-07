package com.example.efficientdet_litert_app

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.efficientdet_litert_app.domain.Category
import com.example.efficientdet_litert_app.domain.toBoundingBoxes
import com.example.efficientdet_litert_app.ui.model.InferenceResultUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val objectDetectorHelper: ObjectDetectorHelper
) : ViewModel() {
    private val _inferenceResultUiState = MutableStateFlow(emptyList<InferenceResultUiModel>())
    val inferenceResultUiState: StateFlow<List<InferenceResultUiModel>> =
        _inferenceResultUiState.asStateFlow()

    fun runInference(data: Bitmap) {
        val result = objectDetectorHelper.runInference(data)
        _inferenceResultUiState.value = result.location
            .toBoundingBoxes()
            .filterIndexed { index, _ -> result.score[index] > 0.5 }
            .mapIndexed { index, boundingBox ->
                InferenceResultUiModel(
                    boundingBox = boundingBox,
                    category = Category.fromId(result.category[index].toInt()),
                    score = result.score[index]
                )
            }

    }
}
