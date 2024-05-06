package com.example.mvi_chatgpt.ui.feature.chat

import android.speech.SpeechRecognizer
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.chat.ChatRole
import com.example.mvi_chatgpt.data.model.mapper.toChatMessages
import com.example.mvi_chatgpt.data.repository.ChatBotRepository
import com.example.mvi_chatgpt.domain.RecordEffect
import com.example.mvi_chatgpt.domain.RecordListener
import com.example.mvi_chatgpt.ui.base.MVIViewModel
import com.example.mvi_chatgpt.ui.common.chat.ChatMessageType
import com.example.mvi_chatgpt.ui.common.chat.UiChatMessage
import com.example.mvi_chatgpt.ui.common.modal.RecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatBotRepository
) : MVIViewModel<ChatContract.Event, ChatContract.State, ChatContract.Effect>() {
    private val currentState get() = viewState.value
    private var isPermissionGranted = false
    val listener = RecordListener()

    init {
        viewModelScope.launch { collectSpeechEffect() }
    }

    override fun setInitialState(): ChatContract.State = ChatContract.State(
        isLoading = false,
        messageList = emptyList()
    )
    override fun handleEvents(event: ChatContract.Event) {
        when(event){
            is ChatContract.Event.InputChange -> setState { copy(inputMessage = event.message) }
            ChatContract.Event.Send -> {
                getChatBotResponse(currentState.inputMessage)
            }

            ChatContract.Event.RequestPermission -> {
                if(isPermissionGranted) {
                    setEffect { ChatContract.Effect.StartRecording }
                    setState { copy(isRecording = true) }
                }
                else {
                    setState { copy(isShowPermission = true) }
                }
            }

            is ChatContract.Event.DismissPermission -> {
                isPermissionGranted = event.isGranted
                if(isPermissionGranted) {
                    setEffect { ChatContract.Effect.StartRecording }
                    setState { copy(isShowPermission = false, isRecording = true) }
                }
                else {
                    setState { copy(isShowPermission = false) }
                }
            }

            ChatContract.Event.DismissBottomSheet -> {
                if(currentState.isRecording) setEffect { ChatContract.Effect.StopRecording }
                setState { copy(isRecording = false, recordState = RecordUiState()) }
            }
        }
    }

    private fun getChatBotResponse(message: String) {
        setState {
            copy(
                isLoading = true,
                inputMessage = "",
                messageList = messageList + UiChatMessage(message, ChatMessageType.USER)
            )
        }

        viewModelScope.launch {
            val allMessages = currentState.messageList.toChatMessages()
            repository.getChatBotResponse(allMessages).collect {
                setState {
                    copy(isLoading = false, messageList = messageList + UiChatMessage(it, ChatMessageType.BOT))
                }
            }
        }
    }

    private suspend fun getRecordBotResponse(message: String): String {
        val chatMessage = com.aallam.openai.api.chat.ChatMessage(ChatRole.User, message)
        return repository.getChatBotResponse(listOf(chatMessage)).first()
    }


    private suspend fun collectSpeechEffect() {
        listener.flow
            .filterNot { effect -> effect is RecordEffect.OnRmsChanged }
            .collect { effect ->
                when(effect) {
                    is RecordEffect.OnReadyForSpeech -> {
                        setState {
                            val recordUiState = recordState.copy(systemMessage = "음성을 듣고 있습니다.")
                            copy(recordState = recordUiState)
                        }
                    }

                    is RecordEffect.OnResults -> {
                        val message = effect.results?.firstOrNull() ?: ""
                        setState { copy(recordState = recordState.copy(systemMessage = "답변을 생성중입니다.", botMessage = "...", userMessage = message)) }
                        val response = getRecordBotResponse(message)
                        setState { copy(recordState = recordState.copy(systemMessage = "음성을 듣고 있습니다.", botMessage = response)) }
                        setEffect { ChatContract.Effect.StartRecording }
                    }

                    is RecordEffect.OnError -> {
                        if(effect.error.code == SpeechRecognizer.ERROR_NO_MATCH) {
                            if(viewState.value.isRecording.not()) return@collect
                            setState { copy(recordState = recordState.copy(systemMessage = effect.error.message)) }
                            delay(2000)
                            setEffect { ChatContract.Effect.StartRecording }
                            return@collect
                        }
                        else if(effect.error.code == SpeechRecognizer.ERROR_CLIENT) {
                            return@collect
                        }

                        setEffect { ChatContract.Effect.ShowSnackBar(effect.error.message) }
                        setState { copy(isRecording = false) }
                    }

                    else -> Unit
                }
            }
    }
}