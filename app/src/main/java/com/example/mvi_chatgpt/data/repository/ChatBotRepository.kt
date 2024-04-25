package com.example.mvi_chatgpt.data.repository

import com.aallam.openai.api.chat.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatBotRepository {
    fun getChatBotResponse(allMessages: List<ChatMessage>): Flow<String>
}