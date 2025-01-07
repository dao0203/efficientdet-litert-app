package com.example.efficientdet_litert_app.ui.model

import androidx.compose.runtime.Stable
import com.example.efficientdet_litert_app.domain.BoundingBox
import com.example.efficientdet_litert_app.domain.Category

@Stable
data class InferenceResultUiModel(
    val boundingBox: BoundingBox,
    val category: Category?,
    val score: Float
)
