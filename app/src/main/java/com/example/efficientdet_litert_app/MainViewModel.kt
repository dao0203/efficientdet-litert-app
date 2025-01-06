package com.example.efficientdet_litert_app

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.efficientdet_litert_app.domain.BoundingBox
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val objectDetectorHelper: ObjectDetectorHelper
) : ViewModel() {
    val _boundingBoxState = MutableStateFlow(emptyList<BoundingBox>())
    val boundingBoxState: StateFlow<List<BoundingBox>> = _boundingBoxState.asStateFlow()
    fun runInference(data: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = objectDetectorHelper.runInference(data)
            // score > 0.5のものだけを抽出
            val filteredResult = result.copy(
                locations = result.locations.filterIndexed { index, _ ->
                    result.score[index] > 0.5
                }
            )
            _boundingBoxState.value = filteredResult.locations
        }
    }
}
