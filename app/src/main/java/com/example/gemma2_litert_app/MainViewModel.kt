package com.example.gemma2_litert_app

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageClassificationHelper: ImageClassificationHelper
) : ViewModel() {
    fun runInference(data: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = imageClassificationHelper.runInference(data)
            println(result)
        }
    }

    override fun onCleared() {
        super.onCleared()
        imageClassificationHelper.close()
    }
}