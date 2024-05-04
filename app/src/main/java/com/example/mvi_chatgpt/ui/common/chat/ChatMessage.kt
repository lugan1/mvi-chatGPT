package com.softnet.temperature.view.ui.component.chat

import java.time.LocalTime

data class ChatMessage(
    val message: String,
    val type: ChatMessageType,
    val time: LocalTime = LocalTime.now()
) {
    val isUser: Boolean
        get() = type == ChatMessageType.USER
}

enum class ChatMessageType {
    USER, BOT
}

fun defaultMessages(): List<ChatMessage> {
    return listOf(
        ChatMessage("안녕하세요! 챗봇입니다\uD83D\uDE0A", ChatMessageType.BOT),
        ChatMessage("문의하실 내용을 간단히 입력하시거나,\n" +
                "아래 버튼을 선택하여 주세요!", ChatMessageType.BOT
        ),
    )
}