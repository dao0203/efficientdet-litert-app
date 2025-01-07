package com.example.efficientdet_litert_app

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.efficientdet_litert_app.domain.BoundingBox
import com.example.efficientdet_litert_app.domain.toBoundingBoxes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val objectDetectorHelper: ObjectDetectorHelper
) : ViewModel() {
    private val _boundingBoxState = MutableStateFlow(emptyList<BoundingBox>())
    val boundingBoxState: StateFlow<List<BoundingBox>> = _boundingBoxState.asStateFlow()
    fun runInference(data: Bitmap) {
        val result = objectDetectorHelper.runInference(data)
        // score > 0.5のものだけを抽出
        _boundingBoxState.value = result.locations.array().toBoundingBoxes()
            .zip(result.score.array().toList())
            .filter { it.second > 0.8 }
            .map { it.first }

    }
}
