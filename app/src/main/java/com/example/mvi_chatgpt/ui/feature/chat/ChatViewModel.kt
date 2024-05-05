package com.example.mvi_chatgpt.ui.feature.chat

import androidx.lifecycle.viewModelScope
import com.example.mvi_chatgpt.data.model.mapper.toChatMessages
import com.example.mvi_chatgpt.data.repository.ChatBotRepository
import com.example.mvi_chatgpt.ui.base.MVIViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatBotRepository: ChatBotRepository
) : MVIViewModel<ChatContract.Event, ChatContract.State, ChatContract.Effect>() {
    private val currentState get() = viewState.value
    private var isPermissionGranted = false

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
                setState { copy(isShowPermission = true) }
            }

            is ChatContract.Event.OnDismissPermission -> {
                if(isPermissionGranted) {
                    setEffect { ChatContract.Effect.StartRecording }
                    setState { copy(isShowPermission = false, isRecording = true) }
                }
                else {
                    setState { copy(isShowPermission = false) }
                }
            }
        }
    }

    private fun getChatBotResponse(message: String) {
        setState {
            copy(
                isLoading = true,
                inputMessage = "",
                messageList = messageList + UiChatMessage(message, ChatRoleType.USER)
            )
        }

        viewModelScope.launch {
            val allMessages = currentState.messageList.toChatMessages()
            chatBotRepository.getChatBotResponse(allMessages).collect {
                setState {
                    copy(isLoading = false, messageList = messageList + UiChatMessage(it, ChatRoleType.SYSTEM))
                }
            }
        }
    }
}