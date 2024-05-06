package com.example.mvi_chatgpt.ui.feature.chat

import com.example.mvi_chatgpt.ui.base.ViewEvent
import com.example.mvi_chatgpt.ui.base.ViewSideEffect
import com.example.mvi_chatgpt.ui.base.ViewState
import com.example.mvi_chatgpt.ui.common.chat.UiChatMessage
import com.example.mvi_chatgpt.ui.common.modal.RecordUiState

class ChatContract {
    sealed class Event: ViewEvent {
        data class InputChange(val message: String): Event()
        data object Send: Event()
        data object RequestPermission: Event()
        data object DismissBottomSheet: Event()
        data class DismissPermission(val isGranted: Boolean): Event()
    }

    data class State(
        val inputMessage: String = "",
        val isLoading: Boolean = false,
        val isShowPermission: Boolean = false,
        val isRecording: Boolean = false,
        val recordState: RecordUiState = RecordUiState(),
        val messageList: List<UiChatMessage> = emptyList(),
    ): ViewState


    sealed class Effect: ViewSideEffect {
        data object StartRecording: Effect()
        data object StopRecording: Effect()

        data class ShowSnackBar(val message: String? = null, val messageRes: Int? = null): Effect()
    }
}