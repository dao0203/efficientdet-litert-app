package com.example.gemma2_litert_app

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gemma2_litert_app.ui.theme.Gemma2litertappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("Hello, Gemma2litertapp!")
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
            )
        }

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
