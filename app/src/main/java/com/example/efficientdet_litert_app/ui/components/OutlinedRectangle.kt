package com.example.efficientdet_litert_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun OutlinedRectangle(
    xMin: Float,
    yMin: Float,
    xMax: Float,
    yMax: Float,
    color: Color = Color.Red,
    strokeWeight: Float = 4f,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawRect(
            topLeft = Offset(xMin, yMin),
            size = Size(
                width = xMax - xMin,
                height = yMax - yMin,
            ),
            color = color,
            style = Stroke(strokeWeight),
        )
    }
}