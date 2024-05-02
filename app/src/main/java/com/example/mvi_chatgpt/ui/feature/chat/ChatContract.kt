package com.example.mvi_chatgpt.ui.feature.chat

import com.example.mvi_chatgpt.ui.base.ViewEvent
import com.example.mvi_chatgpt.ui.base.ViewSideEffect
import com.example.mvi_chatgpt.ui.base.ViewState

class ChatContract {
    sealed class Event: ViewEvent {
        data class InputChange(val message: String): Event()
        data object Send: Event()
        data object RequestPermission: Event()
        data class OnDismissPermission(val isGranted: Boolean): Event()
    }

    data class State(
        val inputMessage: String = "",
        val isLoading: Boolean = false,
        val isShowPermission: Boolean = false,
        val isRecording: Boolean = false,
        val messageList: List<UiChatMessage> = emptyList(),
    ): ViewState


    sealed class Effect: ViewSideEffect {
        data object StartRecording: Effect()
        data object StopRecording: Effect()
    }
}