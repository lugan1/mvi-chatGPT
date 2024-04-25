package com.example.mvi_chatgpt.di

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.example.mvi_chatgpt.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object OpenAiModule {

    @Singleton
    @Provides
    fun provideOpenAI(): OpenAI {
        val config = OpenAIConfig(
            token = BuildConfig.OPENAI_API_KEY,
            timeout = Timeout(socket = 60.seconds),
        )

        return OpenAI(config)
    }
}