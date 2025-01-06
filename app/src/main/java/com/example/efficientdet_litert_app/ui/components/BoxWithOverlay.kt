package com.example.efficientdet_litert_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.efficientdet_litert_app.domain.BoundingBox
import com.example.efficientdet_litert_app.domain.scale

@Composable
fun BoxWithOverlay(
    overlays: List<BoundingBox>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        content()
        overlays.forEach { overlay ->
            Overlay(
                overlay = overlay,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun Overlay(
    overlay: BoundingBox,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        val previewWidth = size.width
        val previewHeight = size.height
        val scaledRoundedBox = overlay.scale(
            yScale = previewHeight,
            xScale = previewWidth,
        )
        drawRect(
            color = Color.Red,
            topLeft = Offset(scaledRoundedBox.xMin, scaledRoundedBox.yMin),
            size = Size(
                width = scaledRoundedBox.xMax - scaledRoundedBox.xMin,
                height = scaledRoundedBox.yMax - scaledRoundedBox.yMin,
            ),
            style = Stroke(4f),
        )
    }
}
