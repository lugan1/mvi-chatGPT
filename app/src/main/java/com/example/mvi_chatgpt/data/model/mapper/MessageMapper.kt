package com.example.mvi_chatgpt.data.model.mapper

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.example.mvi_chatgpt.ui.feature.chat.ChatRoleType
import com.example.mvi_chatgpt.ui.feature.chat.UiChatMessage

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

fun ChatRole.toChatRoleType(): ChatRoleType {
    return when (this) {
        ChatRole.User -> ChatRoleType.USER
        ChatRole.System -> ChatRoleType.SYSTEM
        else -> ChatRoleType.SYSTEM
    }
}

fun ChatRoleType.toChatRole(): ChatRole {
    return when (this) {
        ChatRoleType.USER -> ChatRole.User
        ChatRoleType.SYSTEM -> ChatRole.System
    }
}