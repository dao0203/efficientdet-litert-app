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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.efficentdet_litert_app.R
import com.example.efficientdet_litert_app.domain.BoundingBox
import com.example.efficientdet_litert_app.ui.components.OutlinedRectangle
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

    val boundingBoxState = viewModel.boundingBoxState.collectAsStateWithLifecycle()

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
                boxes = boundingBoxState.value,
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
    boxes: List<BoundingBox>,
    content: @Composable () -> Unit,
) {
    var xScale by remember { mutableIntStateOf(0) }
    var yScale by remember { mutableIntStateOf(0) }
    Box(
        modifier = modifier
            .onGloballyPositioned {
                yScale = it.size.height
                xScale = it.size.width
            }
    ) {
        content()
        boxes.forEach { box ->
            OutlinedRectangle(
                xMin = box.xMin * xScale,
                yMin = box.yMin * yScale,
                xMax = box.xMax * xScale,
                yMax = box.yMax * yScale,
            )
        }
    }
}
