package com.example.matrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.matrix.pages.MainPage.MainPage
import com.example.matrix.ui.theme.MAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAppTheme(darkTheme = true) {
                MainPage()
            }
        }
    }
}