package com.example.mvi_chatgpt.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mvi_chatgpt.ui.navigation.AppNavigation
import com.example.mvi_chatgpt.ui.theme.MVIChatGPTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIChatGPTTheme {
                AppNavigation()
            }
        }
    }
}