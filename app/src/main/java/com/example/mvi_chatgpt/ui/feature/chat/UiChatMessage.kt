package com.example.mvi_chatgpt.ui.feature.chat

import java.time.LocalTime

data class UiChatMessage(
    val message: String,
    val type: ChatRoleType,
    val time: LocalTime = LocalTime.now()
) {
    val isUser: Boolean
        get() = type == ChatRoleType.USER
}

enum class ChatRoleType {
    USER, SYSTEM
}

fun defaultMessages(): List<UiChatMessage> {
    return listOf(
        UiChatMessage("안녕하세요! 챗봇입니다\uD83D\uDE0A", ChatRoleType.SYSTEM),
        UiChatMessage("무엇을 도와드릴까요?", ChatRoleType.SYSTEM)
    )
}