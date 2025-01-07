package com.example.efficientdet_litert_app.domain

import java.nio.FloatBuffer

data class DetectionResult(
    val locations: FloatBuffer,
    val categories: FloatBuffer,
    val score: FloatBuffer,
    val numberOfDetection: FloatBuffer,
)
