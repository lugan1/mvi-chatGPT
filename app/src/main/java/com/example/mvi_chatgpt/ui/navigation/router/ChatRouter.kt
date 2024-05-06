package com.example.mvi_chatgpt.ui.navigation.router

import android.speech.SpeechRecognizer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvi_chatgpt.ui.feature.chat.ChatScreen
import com.example.mvi_chatgpt.ui.feature.chat.ChatViewModel

@Composable
fun ChatRouter(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
            .apply { setRecognitionListener(viewModel.listener) }
    }

    DisposableEffect(key1 = Unit) {
        onDispose { speechRecognizer.destroy() }
    }

    ChatScreen(
        speechRecognizer = speechRecognizer,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEvent =  viewModel::setEvent
    )
}