package com.example.mvi_chatgpt.ui.common.modal

data class RecordUiState(
    val systemMessage : String = "...",
    val userMessage : String = "",
    val botMessage : String = "",
)