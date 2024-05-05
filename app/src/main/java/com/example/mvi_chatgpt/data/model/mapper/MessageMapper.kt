package com.example.mvi_chatgpt.data.model.mapper

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.example.mvi_chatgpt.ui.common.chat.ChatMessageType
import com.example.mvi_chatgpt.ui.common.chat.UiChatMessage

fun ChatMessage.toUiMessage(): UiChatMessage {
    return UiChatMessage(
        message = content.orEmpty(),
        type = role.toChatRoleType()
    )
}
fun List<ChatMessage>.toUiMessages(): List<UiChatMessage> {
    return map { it.toUiMessage() }
}

fun UiChatMessage.toChatMessage(): ChatMessage {
    return ChatMessage(
        content = message,
        role = type.toChatRole()
    )
}

fun List<UiChatMessage>.toChatMessages(): List<ChatMessage> {
    return map { it.toChatMessage() }
}

fun ChatRole.toChatRoleType(): ChatMessageType {
    return when (this) {
        ChatRole.User -> ChatMessageType.USER
        ChatRole.System -> ChatMessageType.BOT
        else -> ChatMessageType.BOT
    }
}

fun ChatMessageType.toChatRole(): ChatRole {
    return when (this) {
          ChatMessageType.USER -> ChatRole.User
          ChatMessageType.BOT -> ChatRole.System
    }
}