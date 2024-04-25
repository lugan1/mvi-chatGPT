package com.example.mvi_chatgpt.ui.navigation.router

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvi_chatgpt.ui.feature.chat.ChatScreen
import com.example.mvi_chatgpt.ui.feature.chat.ChatViewModel

@Composable
fun ChatRouter(
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEvent =  viewModel::setEvent
    )
}