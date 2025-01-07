package com.example.efficientdet_litert_app.domain

import java.nio.FloatBuffer


data class BoundingBox(
    val yMin: Float,
    val xMin: Float,
    val yMax: Float,
    val xMax: Float,
)

fun FloatBuffer.toBoundingBoxes(): List<BoundingBox> {
    val boxes = mutableListOf<BoundingBox>()
    for (i in 0 until this.capacity() step 4) {
        boxes.add(
            BoundingBox(
                yMin = this[i],
                xMin = this[i + 1],
                yMax = this[i + 2],
                xMax = this[i + 3],
            )
        )
    }
    return boxes
}

fun FloatArray.toBoundingBoxes(): List<BoundingBox> {
    val boxes = mutableListOf<BoundingBox>()
    for (i in indices step 4) {
        boxes.add(
            BoundingBox(
                yMin = this[i],
                xMin = this[i + 1],
                yMax = this[i + 2],
                xMax = this[i + 3],
            )
        )
    }
    return boxes
}
