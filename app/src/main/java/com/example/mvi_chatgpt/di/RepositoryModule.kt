package com.example.mvi_chatgpt.di

import com.aallam.openai.client.OpenAI
import com.example.mvi_chatgpt.data.repository.ChatBotRepository
import com.example.mvi_chatgpt.data.repository.ChatBotRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideChatBotRepository(
        openAI: OpenAI
    ): ChatBotRepository {
        return ChatBotRepositoryImpl(openAI)
    }
}