package com.example.mapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mapp.pages.MainPage
import com.example.mapp.ui.theme.MAppTheme

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