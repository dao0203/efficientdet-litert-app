package com.example.gemma2_litert_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.gemma2_litert_app.ui.theme.Gemma2litertappTheme

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
private fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("Hello, Gemma2litertapp!")

            Image(
                painter = painterResource(R.drawable.cat_sample),
                contentDescription = "Cat"
            )
        }
    }
}
