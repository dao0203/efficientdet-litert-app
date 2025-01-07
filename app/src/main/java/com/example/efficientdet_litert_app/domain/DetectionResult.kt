package com.example.efficientdet_litert_app.domain

import java.nio.FloatBuffer

data class DetectionResult(
    val location: FloatBuffer,
    val category: FloatBuffer,
    val score: FloatBuffer,
    val numberOfDetection: FloatBuffer,
)
