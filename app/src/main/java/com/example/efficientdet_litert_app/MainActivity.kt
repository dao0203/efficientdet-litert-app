package com.example.efficientdet_litert_app

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.efficentdet_litert_app.R
import com.example.efficientdet_litert_app.ui.components.OutlinedRectangle
import com.example.efficientdet_litert_app.ui.model.InferenceResultUiModel
import com.example.efficientdet_litert_app.ui.theme.Gemma2litertappTheme
import com.google.android.gms.tflite.java.TfLite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TfLite.initialize(this)
        enableEdgeToEdge()
        setContent {
            Gemma2litertappTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    @DrawableRes val imageRes: Int = R.drawable.cat_sample
    val context = LocalContext.current

    val inferenceResultUiState = viewModel.inferenceResultUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Hello LiteRT!")
            Spacer(Modifier.height(16.dp))
            BoxWithScaledOutlinedRectangles(
                inferenceResults = inferenceResultUiState.value,
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = null,
                )
            }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    val bitmap = BitmapFactory.decodeResource(context.resources, imageRes)
                    viewModel.runInference(bitmap)
                },
            ) {
                Text("Run Inference")
            }
        }
    }
}

@Composable
private fun BoxWithScaledOutlinedRectangles(
    modifier: Modifier = Modifier,
    inferenceResults: List<InferenceResultUiModel>,
    content: @Composable () -> Unit,
) {
    var xScale by remember { mutableIntStateOf(0) }
    var yScale by remember { mutableIntStateOf(0) }
    Box(
        modifier = modifier
            .onGloballyPositioned {
                yScale = it.size.height
                xScale = it.size.width
            },
    ) {
        content()
        inferenceResults.forEach { result ->
            OutlinedRectangleWithText(
                xMin = result.boundingBox.xMin * xScale,
                yMin = result.boundingBox.yMin * yScale,
                xMax = result.boundingBox.xMax * xScale,
                yMax = result.boundingBox.yMax * yScale,
                text = "${result.category} ${result.score}",
            )
        }
    }
}

@Composable
fun OutlinedRectangleWithText(
    xMin: Float,
    yMin: Float,
    xMax: Float,
    yMax: Float,
    text: String,
    topLeft: Offset = Offset(xMin, yMin),
    modifier: Modifier = Modifier,
) {
    val textMeasure = rememberTextMeasurer()
    OutlinedRectangle(
        xMin = xMin,
        yMin = yMin,
        xMax = xMax,
        yMax = yMax,
        topLeft = topLeft,
        modifier = modifier,
    ) {
        val measuredText = textMeasure.measure(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
            ),
        )
        drawRect(
            topLeft = topLeft,
            size = measuredText.size.toSize(),
            color = Color.Red,
        )
        drawText(
            textLayoutResult = measuredText,
            topLeft = topLeft,
        )
    }
}
