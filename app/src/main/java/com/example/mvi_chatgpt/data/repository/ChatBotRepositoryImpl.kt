package com.example.mvi_chatgpt.data.repository

import com.aallam.openai.api.audio.TranscriptionRequest
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.file.FileSourceBuilder
import com.aallam.openai.api.file.fileSource
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val openAI: OpenAI
): ChatBotRepository {
    override fun getChatBotResponse(allMessages: List<ChatMessage>): Flow<String> = flow {
        val chatCompleteRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = allMessages
        )

        val response = openAI.chatCompletion(chatCompleteRequest)
        val botMessage = response.choices
            .first()
            .message
            .content
        println(botMessage)
        botMessage?.let { emit(it) }

    }.flowOn(Dispatchers.IO)
}